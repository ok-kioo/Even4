package br.upe.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class Validation {

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
