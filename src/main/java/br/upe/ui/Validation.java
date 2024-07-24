package br.upe.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidCPF(String cpf) {
        String cpfRegex = "^(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})|(\\d{11})$";
        Pattern pattern = Pattern.compile(cpfRegex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }
}
