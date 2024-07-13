package br.upe.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class User implements Persistence {
    private int id;
    private String name;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void create(Object... params) {
        SecureRandom secureRandom = new SecureRandom();
        if (params.length < 2) {
            //erro
        }
        this.email = (String) params[0];
        this.name = (String) params[1];
        //verificar se o id não existe
        this.id = 100_000_000 + secureRandom.nextInt(900_000_000);
        String line = id + ", " + email + ", " + name;
         try {
            File f = new File("./db/users.txt");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./db/users.txt"))) {
                writer.write(line);
            }
            System.out.println("\nUsuário Criado!\n");
        } catch (IOException writerEx) {
            System.out.println("Error occurred while writing:");
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

    }



}
