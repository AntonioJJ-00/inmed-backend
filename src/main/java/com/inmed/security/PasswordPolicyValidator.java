package com.inmed.security;

import java.util.regex.Pattern;

public final class PasswordPolicyValidator {

    private static final Pattern UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL = Pattern.compile(".*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/].*");

    private PasswordPolicyValidator() {}

    public static boolean isValid(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        return UPPERCASE.matcher(password).matches()
                && LOWERCASE.matcher(password).matches()
                && DIGIT.matcher(password).matches()
                && SPECIAL.matcher(password).matches();
    }
}