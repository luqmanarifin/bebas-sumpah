package com.dancinggrass.prophetaria.bebassumpah;

/**
 * Created by dancinggrass on 4/24/16.
 */
public class Mail {
    public String from;
    public String to;
    public String subject;
    public String message;
    public boolean type_encrypted;
    public boolean type_signature;

    public Mail() {
        this.type_encrypted = false;
        this.type_signature = false;
    }

    public Mail(String from, String to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.type_encrypted = false;
        this.type_signature = false;
    }

    public Mail(String from, String to, String subject, String message, boolean type_encrypted, boolean type_signature) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.subject = subject;
        this.type_encrypted = type_encrypted;
        this.type_signature = type_signature;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public boolean getTypeEncrypted() {
        return type_encrypted;
    }

    public boolean getTypeSignature() {
        return type_signature;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setType_encrypted(boolean type_encrypted) {
        this.type_encrypted = type_encrypted;
    }

    public void setType_signature(boolean type_signature) {
        this.type_signature = type_signature;
    }

    @Override
    public String toString() {
        return "From: " + from;
    }
}
