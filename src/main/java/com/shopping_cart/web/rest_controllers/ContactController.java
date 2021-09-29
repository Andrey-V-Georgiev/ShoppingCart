package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.models.service_models.ContactServiceModel;
import com.shopping_cart.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.shopping_cart.constants.ResponseMsgConstants.FRIENDLY_INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllContacts() {
        try {
            List<ContactServiceModel> contactServiceModelList = this.contactService.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(contactServiceModelList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }
}
