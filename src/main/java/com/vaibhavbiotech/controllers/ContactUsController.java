package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.ContactUs;
import com.vaibhavbiotech.services.ContactUsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ContactUs")
public class ContactUsController {

    @Autowired
    private ContactUsServiceImpl contactUsServiceImpl;

    @PostMapping("/AddContact")
    public ContactUs addContact(@RequestBody ContactUs contactUs) {
        return contactUsServiceImpl.addContact(contactUs);
    }
}
