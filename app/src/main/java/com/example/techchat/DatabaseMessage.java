package com.example.techchat;

public class DatabaseMessage {
    int id=-1;
    String email="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String message="";
    int inOut=-1;
    String time="00.00";
    public DatabaseMessage(){}
    public DatabaseMessage(String email, String message, int inOut, String time) {
        this.email = email;
        this.message = message;
        this.inOut = inOut;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getInOut() {
        return inOut;
    }

    public void setInOut(int inOut) {
        this.inOut = inOut;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
