package com.app.resourceforge.configuration;

import java.security.SecureRandom;

public class UniqueCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PREFIX = "CLIENT-";

    public static String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder(PREFIX);
        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
