package br.upe.persistence;

import java.io.*;
import java.net.UnknownServiceException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Persistence {
    private String id;
    private String name;
    private String email;
    private String password;
    private HashMap<String, User> userHashMap;

    public User (String name, String email, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        try {
            for (Map.Entry<String, User> entry : this.userHashMap.entrySet()) {
                User user = entry.getValue();
                if (user.getId().equals(id)) {  // Exemplo de condição

                }
            }
            this.email = email;

        } catch (IOException exception) {
            System.out.println("Erro ocorreu while writing:");
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        try {
            HashMap<String, User> userHashMap = this.read();

            for (Map.Entry<String, User> entry : userHashMap.entrySet()) {
                User user = entry.getValue();
                if (user.getEmail().equals(email)) {  // Exemplo de condição
                    throw new Exception("Email awlready signed");
                }
            }
            this.email = email;

        } catch (IOException exception) {
            System.out.println("Erro ocorreu while writing:");
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<String, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    @Override
    public void create(Object... params) {
        SecureRandom secureRandom = new SecureRandom();
        if (params.length < 2) {
            // erro
            System.out.println("Erro: Parâmetros insuficientes.");
            return;
        }

        this.email = (String) params[0];
        this.name = (String) params[1];
        // verificar se o id não existe
        this.id = Integer.toString(100_000_000 + secureRandom.nextInt(900_000_000));
        String line = id + ";" + email + ";" + name;

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
    public void read() {
        HashMap<String, User> list = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./db/users.csv"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String email = parts[1].trim();
                    String name = parts[2].trim();

                    User user = new User(id, email, name);
                    list.put(id, user);
                }
            }
            reader.close();

        } catch (IOException readerEx) {
            System.out.println("Error occurred while reading:");
            readerEx.printStackTrace();
        }

        this.userHashMap = list;
    }



}
