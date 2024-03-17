package com.example.servertracker.mail.service;

import com.example.servertracker.mail.entity.MailDetails;

public interface IMailService {
    String sendMailWithAttachment(MailDetails details);
}
