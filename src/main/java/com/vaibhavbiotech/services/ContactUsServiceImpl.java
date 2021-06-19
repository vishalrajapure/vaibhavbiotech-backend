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
        this.sendSelfMail(contactUs);
        return "email sent";
    }

    public void sendMail(ContactUs contactUs) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "143.95.62.230");
        props.put("mail.smtp.port", "26");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@resaleguru.in", "resaleguru@123");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("info@resaleguru.in", "Resale Guru"));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(contactUs.getEmail()));
        msg.setSubject("Registration at Resale Guru");
        //msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Hi " + contactUs.getName() + ", <br></br><br></br>" +
                "We have received your contact details. Our team will contact you soon." + "<br></br><br></br>" +
                "Regards,<br></br>" +
                "Team ResaleGuru", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    public void sendSelfMail(ContactUs contactUs) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "143.95.62.230");
        props.put("mail.smtp.port", "26");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@resaleguru.in", "resaleguru@123");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("ResaleGuru@resaleguru.in", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@resaleguru.in"));
        msg.setSubject("Hi");
        //msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Name: " + contactUs.getName() + "<br></br>" +
                "Email: " + contactUs.getEmail() + "<br></br>" +
                "Message: " + contactUs.getMessage() + "<br></br>", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
