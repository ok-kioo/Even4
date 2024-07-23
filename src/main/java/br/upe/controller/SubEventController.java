package br.upe.controller;


import br.upe.persistence.Event;
import br.upe.persistence.SubEvent;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubEventController implements Controller {
    private HashMap<String, Persistence> subEventHashMap;
    private Persistence subEventLog;

    public SubEventController() {
        this.read();
    }

    public HashMap<String, Persistence> getEventHashMap() {
        return subEventHashMap;
    }

    public void setEventHashMap(HashMap<String, Persistence> subEventHashMap) {
        this.subEventHashMap = subEventHashMap;
    }

    @Override
    public String getData(String dataToGet) {
        String data = "";
        try {
            switch (dataToGet) {
                case "id" -> data = this.subEventLog.getData("id");
                case "name" -> data = this.subEventLog.getData("name");
                case "description" -> data = this.subEventLog.getData("description");
                case "date" -> data = String.valueOf(this.subEventLog.getData("date"));
                case "location" -> data = this.subEventLog.getData("location");
                case "eventId" -> data = this.subEventLog.getData("eventId");
                case "ownerId" -> data = this.subEventLog.getData("ownerId");
                default -> throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Informação não existe ou é restrita");
        }
        return data;
    }

    @Override
    public void create(Object... params) throws FileNotFoundException {
        if (params.length != 6) {
            System.out.println("Só pode ter 6 parametros");
        }

        String eventId = getFatherEventId((String) params[0]);
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String idOwner = (String) params[5];
        Persistence subEvent = new SubEvent();

        try {
            for (Map.Entry<String, Persistence> entry : this.subEventHashMap.entrySet()) {
                Persistence subEventindice = entry.getValue();
                if (subEventindice.getData("name").equals(name)) {
                    throw new IOException();
                }
            }
            if (isValidDate(date)) {
                subEvent.create(eventId, name, date, description, location, idOwner);
            } else {
                throw new IllegalArgumentException("Data inválida: " + date);
            }
        } catch (IOException exception) {
            System.out.println("Nome em uso");
        }
    }

    @Override
    public void delete(Object... params) {
        switch ((String) params[1]) {
            case "name":
                Iterator<Map.Entry<String, Persistence>> iterator = subEventHashMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Persistence> entry = iterator.next();
                    Persistence eventindice = entry.getValue();
                    if (eventindice.getData("name").equals((String) params[0])) {
                        iterator.remove();
                    }
                }
                Persistence subEventPersistence = new SubEvent();
                subEventPersistence.delete(subEventHashMap);
                break;
        }
    }

    @Override
    public void list(String ownerId) {
        try {
            boolean found = false;
            for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
                Persistence persistence = entry.getValue();
                if (persistence.getData("ownerId").equals(ownerId)){
                    System.out.println(persistence.getData("name"));
                    found = true;
                }
            }
            if (!found){
                System.out.println("Seu usuário atual é organizador de nenhum evento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object... params) {
        if (params.length != 5) {
            System.out.println("Só pode ter 5 parametros");
        }

        Persistence subEventPersistence = new SubEvent();
        String name = (String) params[1];
        System.out.println(name);
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String id = "";
        for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])){
                id = persistence.getData("id");
            }
        }

        Persistence newEvent = subEventHashMap.get(id);

        if (newEvent == null) {
            System.out.println("Evento não encontrado");
            return;
        }

        Iterator<Map.Entry<String, Persistence>> iterator = subEventHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Persistence> entry = iterator.next();
            Persistence subEventindice = entry.getValue();
            if (subEventindice.getData("name").equals((String) params[0])) {
                newEvent.setData("name", name);
                newEvent.setData("date", date);
                newEvent.setData("description", description);
                newEvent.setData("location", location);
                subEventHashMap.put(id, newEvent);
            }
        }
        subEventPersistence.update(subEventHashMap);
    }

    @Override
    public void read() {
        Persistence subEventPersistence = new SubEvent();
        this.subEventHashMap = subEventPersistence.read();
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

    private String getFatherEventId(String searchId) throws FileNotFoundException {
        EventController ec = new EventController();
        String fatherId = "";
        HashMap<String, Persistence> list = ec.getEventHashMap();
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listindice = entry.getValue();
            if (listindice.getData("name").equals(searchId)) {
                fatherId = listindice.getData("id");
            }
        }
        return fatherId;
    }
}