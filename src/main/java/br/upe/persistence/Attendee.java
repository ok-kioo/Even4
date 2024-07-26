package br.upe.persistence;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Attendee extends User implements Persistence {
    private String userId;
    private String name;
    private String sessionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public void create(Object... params) {

        if (params.length < 2) {
            System.out.println("Error: Parâmetros insuficientes.");
            return;
        }

        this.userId = (String) params[0];
        this.name = (String) params[1];
        String sessionId= (String) params[2];
        String line = userId + ";" + name + ";" + sessionId;

        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./db/attendees.csv", true))) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Participante Cadastrado");
        } catch (IOException writerEx) {
            System.out.println("Erro na escrita do arquivo");
            writerEx.printStackTrace();
        }
    }


    @Override
    public void update(Object... params) {
        if (params.length > 1) {
            System.out.println("Só pode ter 1 parametro");
        }

        HashMap<String, Persistence> userHashMap = (HashMap<String, Persistence>) params[0];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./db/attendees.csv"))) {
            for (Map.Entry<String, Persistence> entry : userHashMap.entrySet()) {
                Persistence user = entry.getValue();
                String line = user.getData("id") + ";" + user.getData("name") + ";" + user.getData("sessionId") + "\n";
                writer.write(line);
            }
            writer.close();
            System.out.println("Nome Atualizado");
        } catch (IOException writerEx) {
            System.out.println("Erro na escrita do arquivo");
            writerEx.printStackTrace();
        }
    }

    @Override
    public void delete(Object... params) {
        if (params.length > 1) {
            System.out.println("Só pode ter 1 parametro");
        }

        HashMap<String, Persistence> userHashMap = (HashMap<String, Persistence>) params[0];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./db/attendees.csv"))) {
            for (Map.Entry<String, Persistence> entry : userHashMap.entrySet()) {
                Persistence user = entry.getValue();
                String line = user.getData("id") + ";" + user.getData("name") + ";" + user.getData("sessionId") + "\n";
                writer.write(line);
            }
            writer.close();
            System.out.println("Participante Removido");
        } catch (IOException writerEx) {
            System.out.println("Erro na escrita do arquivo");
            writerEx.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Persistence> read() {
        return super.read();
    }
}
