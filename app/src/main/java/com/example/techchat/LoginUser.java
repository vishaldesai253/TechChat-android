package com.example.techchat;

public class LoginUser {
    private String email="";

    public LoginUser(String email) {
        this.email = email;
    }
    public LoginUser() {}

    public String getSender() {
        return email;
    }

}
