package com.crucemelit.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class MessageSender {
    private final Message msg;

    public MessageSender() {
        Session session = Session.getInstance(Utility.getEmailProperties(), Utility.getEmailAuthenticator());
        msg = new MimeMessage(session);
    }

    public final MessageSender setFrom(String email) throws MessagingException {
        msg.setFrom(new InternetAddress(email));
        return this;
    }

    public final MessageSender addRecipient(String email) throws MessagingException {
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        return this;
    }

    public final MessageSender setSubject(String subject) throws MessagingException {
        msg.setSubject(subject);
        return this;
    }

    public final MessageSender setText(String text) throws MessagingException {
        msg.setText(text);
        return this;
    }

    public final void send() throws MessagingException {
        Transport.send(msg);
    }
}