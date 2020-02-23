package com.example.techchat;

public class CurrentData {
    static String senderEmail="";
    static String loginEmail="";
    static String name="";

    public static String getSenderEmail() {
        return senderEmail;
    }

    public static void setSenderEmail(String senderEmail) {
        CurrentData.senderEmail = senderEmail;
    }

    public static String getLoginEmail() {
        return loginEmail;
    }

    public static void setLoginEmail(String loginEmail) {
        CurrentData.loginEmail = loginEmail;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentData.name = name;
    }
}
