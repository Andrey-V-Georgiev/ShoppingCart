package com.shopping_cart.services.impl;

import com.shopping_cart.models.service_models.ContactServiceModel;
import com.shopping_cart.repositories.ContactRepository;
import com.shopping_cart.services.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ContactServiceModel> findAll() {
        return this.contactRepository.findAll().stream()
                .map(o -> this.modelMapper.map(o, ContactServiceModel.class)).collect(Collectors.toList());
    }
}
