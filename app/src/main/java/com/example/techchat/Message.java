package com.example.techchat;

public class Message {
    private String message="";
    private String sender="";

    public Message(String sender,String message) {
        this.message = message;
        this.sender = sender;
    }
    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
