package com.shopping_cart.servicesTesting;

import com.shopping_cart.models.entities.Product;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.CartProductRepository;
import com.shopping_cart.repositories.ProductRepository;
import com.shopping_cart.services.CartProductService;
import com.shopping_cart.services.impl.CartProductServiceImpl;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CartProductServiceTests {

    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private CartProductService cartProductService;

    private final ModelMapper modelMapper = new ModelMapper();
    private ProductServiceModel productServiceModel;

    @Before
    public void init() {
        this.cartProductService = new CartProductServiceImpl(this.modelMapper, this.cartProductRepository);
        this.productServiceModel = new ProductServiceModel(
                "Name",
                "Description",
                "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                BigDecimal.valueOf(700),
                LocalDateTime.now()
        );
    }

    @Test
    public void createCartProduct_correctProductServiceModel_successfullyCreated() {

        Product savedProduct = this.productRepository
                .saveAndFlush(this.modelMapper.map(this.productServiceModel, Product.class));
        CartProductServiceModel cartProductServiceModel = this.cartProductService
                .createCartProduct(this.modelMapper.map(savedProduct, ProductServiceModel.class));

        assertEquals(savedProduct.getName(), cartProductServiceModel.getProduct().getName());
        assertEquals(savedProduct.getDescription(), cartProductServiceModel.getProduct().getDescription());
    }
}
