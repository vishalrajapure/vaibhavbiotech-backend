package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.ContactUs;
import com.vaibhavbiotech.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ConatctUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Override
    public ContactUs addContact(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }
}
