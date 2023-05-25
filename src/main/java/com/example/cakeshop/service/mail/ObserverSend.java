package com.example.cakeshop.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class ObserverSend implements IObserverSend{
    @Override
    public void sendEmail(int i, String s) {

        System.out.println(s);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("assignmentSD32023@gmail.com");
        mailSender.setPassword("wluklnqlikmoiocu");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("assignmentSD32023@gmail.com");
        message.setTo("assignmentSD32023@gmail.com");

        if(i==1){
            message.setSubject("WELCOME TO OUR CAKE SHOP");
        }

        message.setText("User: " + s + " registered successfully.");
        mailSender.send(message);


    }
}
