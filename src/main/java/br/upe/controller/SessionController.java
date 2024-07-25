package br.upe.controller;

import br.upe.persistence.Session;
import br.upe.persistence.Persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionController implements Controller {
    private HashMap<String, Persistence> sessionHashMap;
    private Persistence sessionLog;

    public SessionController() {
        this.read();
    }

    public HashMap<String, Persistence> getSessionHashMap() {
        return sessionHashMap;
    }

    public void setSessionHashMap(HashMap<String, Persistence> sessionHashMap) {
        this.sessionHashMap = sessionHashMap;
    }

    @Override
    public String getData(String dataToGet) {
        String data = "";
        try {
            switch (dataToGet) {
                case "id" -> data = this.sessionLog.getData("id");
                case "name" -> data = this.sessionLog.getData("name");
                case "description" -> data = this.sessionLog.getData("description");
                case "date" -> data = this.sessionLog.getData("date");
                case "location" -> data = this.sessionLog.getData("location");
                case "startTime" -> data = this.sessionLog.getData("startTime");
                case "endTime" -> data = this.sessionLog.getData("endTime");
                case "eventId" -> data = this.sessionLog.getData("eventId");
                case "ownerId" -> data = this.sessionLog.getData("ownerId");
                default -> throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Informação não existe ou é restrita");
        }
        return data;
    }

    @Override
    public void create(Object... params) {
        if (params.length != 8) {
            System.out.println("Número incorreto de parâmetros. Esperado: 8");
            return;
        }

        String eventId = getFatherEventId((String) params[0]);
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String startTime = (String) params[5];
        String endTime = (String) params[6];
        String userId = (String) params[7];

        String eventOwnerId = getFatherOwnerId(eventId);

        if (!eventOwnerId.equals(userId)) {
            System.out.println("Você não pode criar uma sessão para um evento que você não possui.");
            return;
        }

        try {
            for (Map.Entry<String, Persistence> entry : this.sessionHashMap.entrySet()) {
                Persistence sessionIndice = entry.getValue();
                if (sessionIndice.getData("name").equals(name) || name.isEmpty()) {
                    throw new IOException();
                }
            }

            if (isValidDate(date) && isValidTime(startTime) && isValidTime(endTime)) {
                Persistence session = new Session();
                session.create(eventId, name, date, description, location, startTime, endTime, userId);
            } else {
                throw new IllegalArgumentException("Data ou horário inválido");
            }
        } catch (IOException exception) {
            System.out.println("Nome vazio ou em uso\n");
        }
    }

    @Override
    public void delete(Object... params) {
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : sessionHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])) {
                ownerId = persistence.getData("ownerId");
            }
        }

        if (((String) params[1]).equals("name") && ((String) params[2]).equals(ownerId)) {
            Iterator<Map.Entry<String, Persistence>> iterator = sessionHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Persistence> entry = iterator.next();
                Persistence sessionIndice = entry.getValue();
                if (sessionIndice.getData("name").equals((String) params[0])) {
                    iterator.remove();
                }
            }
            Persistence sessionPersistence = new Session();
            sessionPersistence.delete(sessionHashMap);
        } else {
            System.out.println("Você não pode deletar essa Sessão");
        }
    }

    @Override
    public boolean list(String ownerId) {
        boolean isnull = true;
        try {
            boolean found = false;
            for (Map.Entry<String, Persistence> entry : sessionHashMap.entrySet()) {
                Persistence persistence = entry.getValue();
                if (persistence.getData("ownerId").equals(ownerId)) {
                    System.out.println(persistence.getData("name"));
                    found = true;
                    isnull = false;
                }
            }
            if (!found) {
                System.out.println("Seu usuário atual não é organizador de nenhuma Sessão\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isnull;
    }

    @Override
    public void show(String id) {

    }

    @Override
    public void update(Object... params) {
        if (params.length != 9) {
            System.out.println("Número incorreto de parâmetros. Esperado: 9");
            return;
        }

        Persistence sessionPersistence = new Session();
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String startTime = (String) params[5];
        String endTime = (String) params[6];
        String userId = (String) params[8];
        String id = "";
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : sessionHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])) {
                id = persistence.getData("id");
                ownerId = persistence.getData("ownerId");
            }
        }

        if (ownerId.equals(userId)) {
            Persistence newSession = sessionHashMap.get(id);

            if (newSession == null) {
                System.out.println("Sessão não encontrada");
                return;
            }

            for (Map.Entry<String, Persistence> entry : sessionHashMap.entrySet()) {
                Persistence sessionIndice = entry.getValue();
                if (sessionIndice.getData("name").equals((String) params[0])) {
                    newSession.setData("name", name);
                    newSession.setData("date", date);
                    newSession.setData("description", description);
                    newSession.setData("location", location);
                    newSession.setData("startTime", startTime);
                    newSession.setData("endTime", endTime);
                    sessionHashMap.put(id, newSession);
                }
            }

            sessionPersistence.update(sessionHashMap);
        } else {
            System.out.println("Você não pode alterar esta Sessão");
        }
    }

    @Override
    public void read() {
        Persistence sessionPersistence = new Session();
        this.sessionHashMap = sessionPersistence.read();
    }

    @Override
    public boolean loginValidate(String email, String cpf) {
        return false;
    }

    private boolean isValidDate(String dateString) {
        String regex = "^\\d{2}[^\\d]\\d{2}[^\\d]\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateString);
        return matcher.matches();
    }

    private boolean isValidTime(String timeString) {
        String regex = "^\\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeString);
        return matcher.matches();
    }

    private String getFatherEventId(String searchId) {
        EventController ec = new EventController();
        String fatherId = "";
        HashMap<String, Persistence> list = ec.getEventHashMap();
        boolean found = false;
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listIndice = entry.getValue();
            if (listIndice.getData("name").equals(searchId)) {
                fatherId = listIndice.getData("id");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Evento pai não encontrado\n");
        }

        return fatherId;
    }

    private String getFatherOwnerId(String eventId) {
        EventController ec = new EventController();
        String fatherOwnerId = "";
        HashMap<String, Persistence> list = ec.getEventHashMap();
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listIndice = entry.getValue();
            if (listIndice.getData("id").equals(eventId)) {
                fatherOwnerId = listIndice.getData("ownerId");
                break;
            }
        }
        return fatherOwnerId;
    }
}
