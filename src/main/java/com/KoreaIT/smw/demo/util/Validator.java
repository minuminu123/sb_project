package com.KoreaIT.smw.demo.util;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^.*@gmail\\.com$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }
    
    private static final String PASSWORD_REGEX = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{8,}$";
    private static final Pattern pattern2 = Pattern.compile(PASSWORD_REGEX);

    public static boolean isValidPassword(String password) {
        return pattern2.matcher(password).matches();
    }
    
    private static final String PHONE_NUMBER_PATTERN = "^010-\\d{4}-\\d{4}$";

    public static boolean validatePhoneNumber(String cellphoneNum) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        return pattern.matcher(cellphoneNum).matches();
    }
}