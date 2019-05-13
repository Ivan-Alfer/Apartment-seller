package com.apartmentseller.apartmentseller.services;

public interface MailSender {

    void send(String emailTo, String subject, String message);
}
