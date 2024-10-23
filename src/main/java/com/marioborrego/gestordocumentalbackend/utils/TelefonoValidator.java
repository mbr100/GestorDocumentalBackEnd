package com.marioborrego.gestordocumentalbackend.utils;

public class TelefonoValidator {
    public static boolean isTelefonoInValid(String telefono){
        return !telefono.matches("^[0-9]{9}$");
    }
}
