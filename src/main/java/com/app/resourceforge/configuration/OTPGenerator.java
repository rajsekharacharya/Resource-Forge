package com.app.resourceforge.configuration;

import java.util.Random;

public class OTPGenerator {

    private static final int PIN_LENGTH = 6;

    public static String generateOTP() {
        Random random = new Random();

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < PIN_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
