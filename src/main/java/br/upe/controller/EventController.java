package br.upe.controller;
import br.upe.persistence.Event;
import br.upe.persistence.Persistence;
import br.upe.persistence.SubEvent;
import br.upe.persistence.User;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventController implements Controller {
    private HashMap<String, Persistence> eventHashMap;
    private Persistence EventLog;


    public EventController() {
        this.read();
    }

    public HashMap<String, Persistence> getEventHashMap() {
        return eventHashMap;
    }


    public void setEventHashMap(HashMap<String, Persistence> eventHashMap) {
        this.eventHashMap = eventHashMap;
    }

    @Override
    public boolean list(String ownerId){
        this.read();
        boolean isnull = true;
        try {
            boolean found = false;
            for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
                Persistence persistence = entry.getValue();
                if (persistence.getData("ownerId").equals(ownerId)){
                    System.out.println(persistence.getData("name"));
                    found = true;
                    isnull = false;
                }
            }
            if (!found){
                System.out.println("Seu usuário atual é organizador de nenhum evento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isnull;
    }

    @Override
    public void show(String id) {
        this.setEventHashMap(eventHashMap);
        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (!persistence.getData("ownerId").equals(id)){
                System.out.println(persistence.getData("name") + " - " + persistence.getData("id"));
            }
        }
    }

    @Override
    public void update(Object... params) throws FileNotFoundException {
        if (params.length != 6) {
            System.out.println("Só pode ter 6 parametros");
            return;
        }

        String oldName = (String) params[0];
        String newName = (String) params[1];
        String newDate = (String) params[2];
        String newDescription = (String) params[3];
        String newLocation = (String) params[4];
        String userId = (String) params[5];

        boolean isOwner = false;
        String id = null;

        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            String name = persistence.getData("name");
            String ownerId = persistence.getData("ownerId");

            if (name != null && name.equals(oldName) && ownerId != null && ownerId.equals(userId)) {
                isOwner = true;
                id = persistence.getData("id");
                break;
            }
        }

        if (isOwner) {
            boolean nameExists = false;
            for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
                Persistence subEvent = entry.getValue();
                String name = subEvent.getData("name");
                if (name.isEmpty() || name.equals(newName)) {
                    nameExists = true;
                    break;
                }
            }

            if (nameExists) {
                System.out.println("Nome em uso ou vazio");
                return;
            }

            if (id != null) {
                Persistence newSubEvent = eventHashMap.get(id);
                if (newSubEvent != null) {
                    newSubEvent.setData("name", newName);
                    newSubEvent.setData("date", newDate);
                    newSubEvent.setData("description", newDescription);
                    newSubEvent.setData("location", newLocation);
                    eventHashMap.put(id, newSubEvent);
                    Persistence subEventPersistence = new SubEvent();
                    subEventPersistence.update(eventHashMap);
                } else {
                    System.out.println("SubEvento não encontrado");
                }
            } else {
                System.out.println("Você não pode alterar este SubEvento");
            }
        } else {
            System.out.println("Você não pode alterar este SubEvento");
        }
    }


    @Override
    public void read() {
        Persistence eventPersistence = (Persistence) new Event();
        this.eventHashMap = eventPersistence.read();
    }


    @Override
    public boolean loginValidate(String email, String cpf) {
        return false;
    }


    @Override
    public String getData(String dataToGet) {
        return null;
    }

    @Override
    public void create(Object... params) {
        if (params.length != 5) {
            System.out.println("Só pode ter 5 parâmetros");
            return;
        }

        String name = (String) params[0];
        String date = (String) params[1];
        String description = (String) params[2];
        String location = (String) params[3];
        String idOwner = (String) params[4];

        for (Map.Entry<String, Persistence> entry : this.eventHashMap.entrySet()) {
            Persistence event = entry.getValue();
            if (event.getData("name").equals(name) || name.isEmpty()) {
                System.out.println("Nome em uso ou vazio");
                return;
            }
        }

        Persistence event = new Event();
        event.create(name, date, description, location, idOwner);

    }

    @Override
    public void delete(Object... params) {
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])){
                ownerId = persistence.getData("ownerId");
            }
        }

        if (((String) params[1]).equals("name") && ((String) params[2]).equals(ownerId)) {
            Iterator<Map.Entry<String, Persistence>> iterator = eventHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Persistence> entry = iterator.next();
                Persistence eventindice = entry.getValue();
                if (eventindice.getData("name").equals((String) params[0])) {
                    iterator.remove();
                }
            }
            Persistence eventPersistence = new Event();
            eventPersistence.delete(eventHashMap);
        } else {
            System.out.println("Você não pode deletar esse evento");
        }
    }

}