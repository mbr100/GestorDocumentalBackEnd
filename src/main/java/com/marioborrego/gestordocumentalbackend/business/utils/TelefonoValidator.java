package com.marioborrego.gestordocumentalbackend.business.utils;

public class TelefonoValidator {
    public static boolean isTelefonoInValid(String telefono){
        return !telefono.matches("^[0-9]{9}$");
    }
}
