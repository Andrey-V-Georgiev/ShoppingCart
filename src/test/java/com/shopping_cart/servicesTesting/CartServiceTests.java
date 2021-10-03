package com.shopping_cart.servicesTesting;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.entities.Cart;
import com.shopping_cart.models.entities.Product;
import com.shopping_cart.models.entities.User;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.models.view_models.CartViewModel;
import com.shopping_cart.repositories.CartProductRepository;
import com.shopping_cart.repositories.CartRepository;
import com.shopping_cart.repositories.ProductRepository;
import com.shopping_cart.repositories.UserRepository;
import com.shopping_cart.services.CartProductService;
import com.shopping_cart.services.CartService;
import com.shopping_cart.services.ProductService;
import com.shopping_cart.services.impl.CartProductServiceImpl;
import com.shopping_cart.services.impl.CartServiceImpl;
import com.shopping_cart.services.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.shopping_cart.constants.UserRoleConstants.ROLE_ADMIN;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CartServiceTests {

    @Autowired
    private CartRepository cartRepository;
    @MockBean
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;

    @Autowired
    private CartProductRepository cartProductRepository;
    @MockBean
    private CartProductService cartProductService;

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private Cart cart;
    private User user;
    private Product product1;
    private Product product2;

    @Before
    public void init() {
        this.productService = new ProductServiceImpl(
                this.modelMapper,
                this.productRepository
        );
        this.cartProductService = new CartProductServiceImpl(
                this.modelMapper,
                this.cartProductRepository
        );
        this.cartService = new CartServiceImpl(
                this.modelMapper,
                this.cartRepository,
                this.productService,
                this.cartProductService
        );
        this.user = new User(
                "Username",
                "email@email.com",
                "password",
                "token",
                ROLE_ADMIN,
                LocalDateTime.now()
        );
        this.product1 = new Product(
                "Name 1",
                "Description",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(1000),
                LocalDateTime.now()
        );
        this.product2 = new Product(
                "Name 2",
                "Description",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(1000),
                LocalDateTime.now()
        );
        this.cart = new Cart(this.user);
    }

    @Test
    public void findCartByUserId_correctUserId_returnCartServiceModelSuccessfully() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        Cart savedCart = this.cartRepository.saveAndFlush(this.cart);
        CartServiceModel cartByUserId = this.cartService.findCartByUserId(savedCart.getUser().getId());
        assertEquals(savedUser.getId(), cartByUserId.getUser().getId());
    }

    @Test
    public void findCartByUserId_fakeUserId_returnNull() {
        CartServiceModel cartByUserId = this.cartService.findCartByUserId("fake");
        assertNull(cartByUserId);
    }

    @Test
    public void addProductToCart_correctUserIds_returnProductServiceModelSuccessfully() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct = this.productRepository.saveAndFlush(this.product1);
        ProductServiceModel productServiceModel = this.cartService.addProductToCart(savedUser.getId(), savedProduct.getId());
        assertEquals(savedProduct.getId(), productServiceModel.getId());
    }

    @Test
    public void addProductToCart_fakeProductId_returnNull() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        ProductServiceModel productServiceModel = this.cartService.addProductToCart(savedUser.getId(), "fake");
        assertNull(productServiceModel);
    }

    @Test
    public void decreaseProductCount_fakeProductId_returnNull() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        RemoveProductFromCart removeProductFromCart = this.cartService.decreaseProductCount("fake", savedUser.getId());
        assertNull(removeProductFromCart);
    }

    @Test
    public void decreaseProductCount_correctProductId_decreaseProductCount() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct = this.productRepository.saveAndFlush(this.product1);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct.getId());
        Integer before = this.cartService.findCartByUserId(savedUser.getId()).getCartProducts().get(0).getQuantity();

        this.cartService.decreaseProductCount(savedProduct.getId(), savedUser.getId());
        Integer after = this.cartService.findCartByUserId(savedUser.getId()).getCartProducts().get(0).getQuantity();

        assertNotEquals(before, after);
    }

    @Test
    public void removeProductFromCartList_correctArgs_removeProductSuccessfully() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);
        Product savedProduct2 = this.productRepository.saveAndFlush(this.product2);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct2.getId());

        CartServiceModel cartServiceModelBefore = this.cartService.findCartByUserId(savedUser.getId());
        List<CartProductServiceModel> cartProductServiceModelsBefore = cartServiceModelBefore.getCartProducts();
        CartProductServiceModel cartProductServiceModel = cartProductServiceModelsBefore.get(0);
        Integer before = cartProductServiceModelsBefore.size();

        this.cartService.removeProductFromCartList(
                cartProductServiceModel,
                this.modelMapper.map(cartServiceModelBefore, CartServiceModel.class));

        CartServiceModel cartServiceModelAfter = this.cartService.findCartByUserId(savedUser.getId());
        List<CartProductServiceModel> cartProductServiceModelsAfter = cartServiceModelAfter.getCartProducts();
        Integer after = cartProductServiceModelsAfter.size();

        assertNotEquals(before, after);
    }

    @Test
    public void emptyTheCart_correctUserId_emptyCartSuccessfully() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);
        Product savedProduct2 = this.productRepository.saveAndFlush(this.product2);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct2.getId());

        CartServiceModel cartServiceModelBefore = this.cartService.findCartByUserId(savedUser.getId());
        List<CartProductServiceModel> cartProductServiceModelsBefore = cartServiceModelBefore.getCartProducts();
        Integer before = cartProductServiceModelsBefore.size();

        this.cartService.emptyTheCart(savedUser.getId());

        CartServiceModel cartServiceModelAfter = this.cartService.findCartByUserId(savedUser.getId());
        List<CartProductServiceModel> cartProductServiceModelsAfter = cartServiceModelAfter.getCartProducts();
        Integer after = cartProductServiceModelsAfter.size();

        assertNotEquals(before, after);
    }

    @Test
    public void checkCartIsEmpty_cartIsEmpty_returnTrue() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
         this.cartRepository.saveAndFlush(this.cart);
        boolean checkCartIsEmpty = this.cartService.checkCartIsEmpty(savedUser.getId());
        assertTrue(checkCartIsEmpty);
    }

    @Test
    public void checkCartIsEmpty_cartIsNotEmpty_returnFalse() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);
        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());

        boolean checkCartIsEmpty = this.cartService.checkCartIsEmpty(savedUser.getId());
        assertFalse(checkCartIsEmpty);
    }

    @Test
    public void calculateCartDiscount_oneProduct_noDiscount() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        CartServiceModel cartServiceModel = this.cartService.findCartByUserId(savedUser.getId());
        CartViewModel cartViewModel = this.cartService.calculateCartDiscount(cartServiceModel);

        assertEquals(BigDecimal.valueOf(1000), cartViewModel.getTotalPriceProducts());
        assertEquals(BigDecimal.valueOf(1000), cartViewModel.getTotalPriceAfterQuantityDiscount());
        assertEquals(BigDecimal.valueOf(1000), cartViewModel.getTotalPriceAfterAllSumDiscounts());
        assertEquals(BigDecimal.valueOf(0), cartViewModel.getFinalDiscountInMoney());
        Double expected = 0d;
        Double actual = cartViewModel.getFinalDiscountInPercent();
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCartDiscount_productQuantityTwo_productQuantityDiscount10Percent() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        CartServiceModel cartServiceModel = this.cartService.findCartByUserId(savedUser.getId());
        CartViewModel cartViewModel = this.cartService.calculateCartDiscount(cartServiceModel);

        assertEquals(BigDecimal.valueOf(2000), cartViewModel.getTotalPriceProducts());
        assertEquals(BigDecimal.valueOf(1800), cartViewModel.getTotalPriceAfterQuantityDiscount());
        assertEquals(BigDecimal.valueOf(1800), cartViewModel.getTotalPriceAfterAllSumDiscounts());
        assertEquals(BigDecimal.valueOf(200), cartViewModel.getFinalDiscountInMoney());
        Double expected = 10d;
        Double actual = cartViewModel.getFinalDiscountInPercent();
        assertEquals(expected, actual);
    }

    @Test
    public void calculateCartDiscount_totalSumMoreThan3000_sumDiscount10PercentOverQuantityDiscount() {
        User savedUser = this.userRepository.saveAndFlush(this.user);
        this.cart.setUser(savedUser);
        this.cartRepository.saveAndFlush(this.cart);
        Product savedProduct1 = this.productRepository.saveAndFlush(this.product1);

        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        this.cartService.addProductToCart(savedUser.getId(), savedProduct1.getId());
        CartServiceModel cartServiceModel = this.cartService.findCartByUserId(savedUser.getId());
        CartViewModel cartViewModel = this.cartService.calculateCartDiscount(cartServiceModel);

        assertEquals(BigDecimal.valueOf(4000), cartViewModel.getTotalPriceProducts());
        assertEquals(BigDecimal.valueOf(3600), cartViewModel.getTotalPriceAfterQuantityDiscount());
        assertEquals(BigDecimal.valueOf(3240), cartViewModel.getTotalPriceAfterAllSumDiscounts());
        assertEquals(BigDecimal.valueOf(760), cartViewModel.getFinalDiscountInMoney());
        Double expected = 19d;
        Double actual = cartViewModel.getFinalDiscountInPercent();
        assertEquals(expected, actual);
    }
}
