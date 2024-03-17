package com.example.servertracker.mail.controller;

import com.example.servertracker.mail.entity.MailDetails;
import com.example.servertracker.mail.service.IMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    IMailService mailService;

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody MailDetails details) throws MessagingException {
        System.out.println("Hello");

        String status =mailService.sendMailWithAttachment(details);
        return status;

    }

}
