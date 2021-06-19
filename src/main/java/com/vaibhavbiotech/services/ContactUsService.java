package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.ContactUs;

import javax.mail.MessagingException;
import java.io.IOException;

public interface ContactUsService {
    ContactUs addContact(ContactUs contactUs) throws MessagingException, IOException;
}
