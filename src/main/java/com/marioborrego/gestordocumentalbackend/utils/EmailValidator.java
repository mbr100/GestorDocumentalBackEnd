package com.marioborrego.gestordocumentalbackend.utils;

public class EmailValidator {
    public static boolean isEmailInValid(String email){
        return !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}
