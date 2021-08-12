package com.shopping_cart.services;

import com.shopping_cart.models.service_models.ContactServiceModel;

import java.util.List;


public interface ContactService {

    List<ContactServiceModel> findAll();
}
