package com.shopping_cart.services.impl;

import com.shopping_cart.models.binding_models.ProductBindingModel;
import com.shopping_cart.models.entities.Product;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.ProductRepository;
import com.shopping_cart.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductServiceModel findById(String id) {
        return this.productRepository.findById(id)
                .map(o -> this.modelMapper.map(o, ProductServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<ProductServiceModel> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel addProduct(ProductBindingModel productBindingModel) {
        Product checkExists = this.productRepository.findProductByName(productBindingModel.getName()).orElse(null);
        if (checkExists != null) {
            return null;
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productBindingModel, ProductServiceModel.class);
        productServiceModel.setAddedOn(LocalDateTime.now());
        Product product = this.productRepository.saveAndFlush(this.modelMapper.map(productServiceModel, Product.class));
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductBindingModel productBindingModel) {
        Product productOld = this.productRepository.findById(id).orElse(null);
        if (productOld == null) {
            return null;
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productBindingModel, ProductServiceModel.class);
        productServiceModel.setId(productOld.getId());
        productServiceModel.setAddedOn(productOld.getAddedOn());
        Product productUpdated = this.productRepository
                .saveAndFlush(this.modelMapper.map(productServiceModel, Product.class));
        return this.modelMapper.map(productUpdated, ProductServiceModel.class);
    }

    @Override
    public int removeById(String id) {
        Product checkExists = this.productRepository.findById(id).orElse(null);
        if (checkExists == null) {
            return -1;
        }
        this.productRepository.deleteById(id);
        return 1;
    }

    @Override
    public List<ProductServiceModel> findByKeyword(String keyword) {
        return this.productRepository.findProductByKeyword(keyword).stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class)).collect(Collectors.toList());
    }
}
