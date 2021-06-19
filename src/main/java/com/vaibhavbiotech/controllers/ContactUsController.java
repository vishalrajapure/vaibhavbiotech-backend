package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.ContactUs;
import com.vaibhavbiotech.services.ContactUsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/ContactUs")
public class ContactUsController {

    @Autowired
    private ContactUsServiceImpl contactUsServiceImpl;

    @PostMapping("/AddContact")
    public ContactUs addContact(@RequestBody ContactUs contactUs) throws MessagingException, IOException {
        return contactUsServiceImpl.addContact(contactUs);
    }
}
