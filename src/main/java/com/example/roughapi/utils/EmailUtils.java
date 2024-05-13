package com.example.roughapi.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account.\n\n" + getVerificationURL(host, token) + "\n\n" + "The support team";
    }

    private static String getVerificationURL(String host, String token) {
        return host + "/app/contact/verify?token=" + token;
    }
}
