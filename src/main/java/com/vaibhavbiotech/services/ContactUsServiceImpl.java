package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.ContactUs;
import com.vaibhavbiotech.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Override
    public ContactUs addContact(ContactUs contactUs) throws AddressException, MessagingException, IOException {
        ContactUs storedContact = contactUsRepository.save(contactUs);
        this.sendDetails(contactUs);
        return storedContact;
    }

    public String sendDetails(ContactUs contactUs) throws MessagingException, IOException {
        this.sendMail(contactUs);
        return "email sent";
    }

    public void sendMail(ContactUs contactUs) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.hostinger.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@vaibhavbiotech.com", "Vaibhavbiotech@123");
            }
        });

        //sending email to client
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("info@vaibhavbiotech.com", "Vaibhav Biotech"));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(contactUs.getEmail()));
        msg.setSubject("Registration at Vaibhav Biotech");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Hi " + contactUs.getName() + ", <br></br><br></br>" +
                "We have received your contact details. Our team will contact you soon." + "<br></br><br></br>" +
                "Regards,<br></br>" +
                "Team Vaibhav Biotech", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);

        //Sending self email

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@vaibhavbiotech.com"));
        msg.setSubject("Registration at Vaibhav Biotech");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent("Enquiry Received From: <br></br><br></br> Name: " + contactUs.getName() + "<br></br>" +
                "Email: " + contactUs.getEmail() + "<br></br>" +
                "Contact: " + contactUs.getPhone() + "<br></br>" +
                "Message: " + contactUs.getMessage() + "<br></br>", "text/html");

        Multipart multipart1 = new MimeMultipart();
        multipart1.addBodyPart(messageBodyPart1);
        msg.setContent(multipart1);
        Transport.send(msg);

    }
}
