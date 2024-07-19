package br.upe.persistence;

import br.upe.controller.Controller;
import br.upe.controller.UserController;

import java.io.*;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;

public class User implements Persistence {
    private String id;
    private String cpf;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String generateId() {
        SecureRandom secureRandom = new SecureRandom();
        long timestamp = Instant.now().toEpochMilli();
        int lastThreeDigitsOfTimestamp = (int) (timestamp % 1000); // Get the last 3 digits
        int randomValue = secureRandom.nextInt(900) + 100; // 3-digit random number
        return String.format("%03d%03d", lastThreeDigitsOfTimestamp, randomValue); // Format to ensure 6 digits
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void create(Object... params) {

        if (params.length < 2) {
            // erro
            System.out.println("Erro: Parâmetros insuficientes.");
            return;
        }

        this.email = (String) params[0];
        this.cpf = (String) params[1];
        //timestamp
        this.id = generateId();
        String line = id + ";" + email + ";" + cpf;

        try {
            File f = new File("./db/users.csv");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./db/users.csv", true))) {
                writer.write(line);
                writer.newLine(); // Adiciona uma nova linha após escrever os dados
            }

            System.out.println("\nUsuário Criado!\n");
        } catch (IOException writerEx) {
            System.out.println("Error ocurred while writing:");
            writerEx.printStackTrace();
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public HashMap<String, User> read() {
        HashMap<String, User> list = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./db/users.csv"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String email = parts[1].trim();
                    String cpf = parts[2].trim();

                    User user = new User();
                    user.setEmail(email);
                    user.setCpf(cpf);
                    user.setId(id);
                    list.put(user.getId(), user);
                }
            }
            reader.close();

        } catch (IOException readerEx) {
            System.out.println("Error occurred while reading:");
            readerEx.printStackTrace();
        }

        return list;
    }

}
