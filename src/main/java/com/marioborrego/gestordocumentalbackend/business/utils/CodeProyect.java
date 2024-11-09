package com.marioborrego.gestordocumentalbackend.business.utils;

public class CodeProyect {
    public static String idToCodeProyect(int number) {
        String numberStr = String.format("%09d", number);
        StringBuilder formatted = new StringBuilder();

        int length = numberStr.length();

        for (int i = 0; i < length; i++) {
            formatted.append(numberStr.charAt(i));
            if ((length - i - 1) % 3 == 0 && i != length - 1) {
                formatted.append('.');
            }
        }
        return formatted.toString();
    }

    public static int codeProyectToId(String code) {
        // Eliminar los puntos del código
        String cleanedCode = code.replace(".", "");
        // Convertir la cadena a un número
        return Integer.parseInt(cleanedCode);
    }

    public static Long decode(String code) {
        String cleanedCode = code.replace(".", "");
        return Long.parseLong(cleanedCode);
    }
}
