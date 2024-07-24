package br.upe.ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public boolean isValidDate(String dt) {
        String[] date = dt.split("/");

        if (date.length != 3) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
            return false;
        }

        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        try {
            LocalDate dateValidate = LocalDate.of(year, month, day);
            LocalDate dateNow = LocalDate.now();

            if (dateValidate.isBefore(dateNow)) {
                System.out.println("A data '" + dt + "' é anterior à data de hoje.");
                return false;
            }

            return true;
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("Erro ao parsear a data: " + e.getMessage());
            return false;
        }
    }
}

