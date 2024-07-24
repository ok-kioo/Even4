package br.upe.controller;


import br.upe.persistence.Event;
import br.upe.persistence.SubEvent;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            System.out.println("Só pode ter 6 parâmetros");
            return;
        }

        String eventId = getFatherEventId((String) params[0]);
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String userId = (String) params[5];

        String eventOwnerId = getFatherOwnerId(eventId);

        if (!eventOwnerId.equals(userId)) {
            System.out.println("Você não pode criar um subevento para um evento que você não possui.");
            return;
        }

        boolean nomeEmUso = false;
        for (Map.Entry<String, Persistence> entry : this.subEventHashMap.entrySet()) {
            Persistence subEvent = entry.getValue();
            if (subEvent.getData("name").equals(name)) {
                nomeEmUso = true;
                break; //
            }
        }

        if (nomeEmUso || name.isEmpty()) {
            System.out.println("Nome vazio ou em uso");
            return;
        }

        Persistence subEvent = new SubEvent();
        subEvent.create(eventId, name, date, description, location, userId);
    }


    @Override
    public void delete(Object... params) {
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])){
                ownerId = persistence.getData("ownerId");
            }
        }

        if (((String) params[1]).equals("name") && ((String) params[2]).equals(ownerId)) {
            Iterator<Map.Entry<String, Persistence>> iterator = subEventHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Persistence> entry = iterator.next();
                Persistence subEventindice = entry.getValue();
                if (subEventindice.getData("name").equals((String) params[0])) {
                    iterator.remove();
                }
            }
            Persistence SubEventPersistence = new SubEvent();
            SubEventPersistence.delete(subEventHashMap);
        } else {
            System.out.println("Você não pode deletar esse SubEvento");
        }
    }

    @Override
    public boolean list(String ownerId) {
        boolean isnull = true;
        try {
            boolean found = false;
            for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
                Persistence persistence = entry.getValue();
                if (persistence.getData("ownerId").equals(ownerId)){
                    System.out.println(persistence.getData("name"));
                    found = true;
                    isnull = false;
                }
            }
            if (!found){
                System.out.println("Seu usuário atual é organizador de nenhum SubEvento\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isnull;
    }

    @Override
    public void show(String id) {
        for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (!persistence.getData("ownerId").equals(id)){
                System.out.println(persistence.getData("name"));
            }
        }
    }

    @Override
    public void update(Object... params) throws FileNotFoundException {
        if (params.length != 6) {
            System.out.println("Só pode ter 6 parametros");
        }

        Persistence subEventPersistence = new SubEvent();
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String userId = (String) params[5];
        String id = "";
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])) {
                id = persistence.getData("id");
                ownerId = persistence.getData("ownerId");
            }
        }

        if (ownerId.equals(userId)) {
            Persistence newSubEvent = subEventHashMap.get(id);

            if (newSubEvent == null) {
                System.out.println("SubEvento não encontrado");
                return;
            }

            for (Map.Entry<String, Persistence> entry : subEventHashMap.entrySet()) {
                Persistence subEventindice = entry.getValue();
                if (subEventindice.getData("name").equals((String) params[0])) {
                    newSubEvent.setData("name", name);
                    newSubEvent.setData("date", date);
                    newSubEvent.setData("description", description);
                    newSubEvent.setData("location", location);
                    subEventHashMap.put(id, newSubEvent);
                }
            }

            subEventPersistence.update(subEventHashMap);
        } else {
            System.out.println("Você não pode alterar este SubEvento");
        }
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

    private String getFatherEventId(String searchId) throws FileNotFoundException {
        EventController ec = new EventController();
        String fatherId = "";
        HashMap<String, Persistence> list = ec.getEventHashMap();
        boolean found = false;
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listindice = entry.getValue();
            if (listindice.getData("name").equals(searchId)) {
                fatherId = listindice.getData("id");
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("Evento pai não encontrado\n");

        }

        return fatherId;
    }

    private String getFatherOwnerId(String eventId) throws FileNotFoundException {
        EventController ec = new EventController();
        String fatherOwnerId = "";
        HashMap<String, Persistence> list = ec.getEventHashMap();
        for (Map.Entry<String, Persistence> entry : list.entrySet()) {
            Persistence listindice = entry.getValue();
            if (listindice.getData("id").equals(eventId)) {
                fatherOwnerId = listindice.getData("ownerId");
                break;
            }
        }
        return fatherOwnerId;
    }

}