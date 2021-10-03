package com.shopping_cart.servicesTesting;

import com.shopping_cart.models.binding_models.ProductBindingModel;
import com.shopping_cart.models.entities.Product;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.ProductRepository;
import com.shopping_cart.services.ProductService;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductServiceTests {

    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;

    private final ModelMapper modelMapper = new ModelMapper();
    private Product product;
    private ProductBindingModel productBindingModel;

    @Before
    public void init() {
        this.productService = new ProductServiceImpl(this.modelMapper, this.productRepository);
        this.product = new Product(
                "Name",
                "Description",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(700),
                LocalDateTime.now()
        );
        this.productBindingModel = new ProductBindingModel(
                "Name",
                "Description",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(700)
        );
    }

    @Test
    public void findById_whenIdIsValid_correctProductFound() {
        Product expected = this.productRepository.saveAndFlush(this.product);
        ProductServiceModel actual = this.productService.findById(expected.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void findById_whenIdIsNotValid_returnNull() {
        ProductServiceModel actual = this.productService.findById("false");
        assertNull(actual);
    }

    @Test
    public void findAll_withZeroProduct_arrayIsEmpty() {
        List<ProductServiceModel> allProductServiceModels = this.productService.findAll();
        assertTrue(allProductServiceModels.isEmpty());
    }

    @Test
    public void findAll_withOneProduct_returnTheProduct() {
        Product expected = this.productRepository.saveAndFlush(this.product);
        List<ProductServiceModel> allProductServiceModels = this.productService.findAll();
        ProductServiceModel actual = allProductServiceModels.get(0);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void addProduct_existingProduct_returnNull() {
        this.productRepository.saveAndFlush(this.product);
        ProductServiceModel addedProduct = this.productService.addProduct(this.productBindingModel);
        assertNull(addedProduct);
    }

    @Test
    public void addProduct_newProduct_successfullyAdded() {
        ProductServiceModel expected = this.productService.addProduct(this.productBindingModel);
        ProductServiceModel actual = this.productService.findById(expected.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void editProduct_unexistingId_returnNull() {
        ProductServiceModel editedProduct = this.productService.editProduct("false", this.productBindingModel);
        assertNull(editedProduct);
    }

    @Test
    public void editProduct_existingProduct_editedSuccessfully() {
        ProductServiceModel addedProduct = this.productService.addProduct(this.productBindingModel);
        this.productBindingModel = new ProductBindingModel(
                "Name Edited",
                "Description Edited",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(700)
        );
        ProductServiceModel editedProduct = this.productService.editProduct(addedProduct.getId(), this.productBindingModel);
        assertEquals("Name Edited", editedProduct.getName());
        assertEquals("Description Edited", editedProduct.getDescription());
    }

    @Test
    public void removeById_unexistingId_returnMinusOne() {
        int actual = this.productService.removeById("false");
        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    public void removeById_existingId_returnOne() {
        ProductServiceModel addedProduct = this.productService.addProduct(this.productBindingModel);
        int actual = this.productService.removeById(addedProduct.getId());
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void findByKeyword_unexistingProductName_arrayIsEmpty() {
        String keyword = "Keyword";
        this.productService.addProduct(this.productBindingModel);
        List<ProductServiceModel> productServiceModelsByKeyword = this.productService.findByKeyword(keyword);
        assertTrue(productServiceModelsByKeyword.isEmpty());
    }

    @Test
    public void findByKeyword_existingProductName_returnCorrectProductServiceModel() {
        String keyword = "Keyword";
        this.productBindingModel = new ProductBindingModel(
                keyword,
                "Description Edited",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(700)
        );
        ProductServiceModel expected = this.productService.addProduct(this.productBindingModel);
        List<ProductServiceModel> productServiceModelsByKeyword = this.productService.findByKeyword(keyword);
        ProductServiceModel actual = productServiceModelsByKeyword.get(0);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }
}
