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
        String cpfRegex = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})|(\\d{11})$";
        Pattern pattern = Pattern.compile(cpfRegex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }

    public static boolean isValidDate(String dt) {
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

    public static boolean areValidTimes(String startTime, String endTime) {
        if (!isValidTime(startTime)) {
            return false;
        }

        if (!isValidTime(endTime)) {
            return false;
        }
        return true;
    }

    public static boolean isValidTime(String hr) {
        String[] time = hr.split(":");

        if (time.length != 2) {
            System.out.println("Formato de hora inválido. Use o formato hh:mm.");
            return false;
        }

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        if (hour < 0 || hour > 23) {
            System.out.println("Horário inválido");
            return false;
        }

        if (minute < 0 || minute > 59) {
            System.out.println("Horário inválido");
            return false;
        }

        return true;

    }
}

