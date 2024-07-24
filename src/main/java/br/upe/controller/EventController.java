package br.upe.controller;
import br.upe.persistence.Event;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;


import java.io.IOException;
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
    public void update(Object... params) {
        if (params.length != 6) {
            System.out.println("Só pode ter 6 parametros");
        }

        Persistence eventPersistence = new Event();
        String name = (String) params[1];
        String date = (String) params[2];
        String description = (String) params[3];
        String location = (String) params[4];
        String userId = (String) params[5];
        String id = "";
        String ownerId = "";
        for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
            Persistence persistence = entry.getValue();
            if (persistence.getData("name").equals((String) params[0])){
                id = persistence.getData("id");
                ownerId = persistence.getData("ownerId");
            }
        }

        if(ownerId.equals(userId)) {
            Persistence newEvent = eventHashMap.get(id);

            if (newEvent == null) {
                System.out.println("Evento não encontrado");
                return;
            }

            for (Map.Entry<String, Persistence> entry : eventHashMap.entrySet()) {
                Persistence eventindice = entry.getValue();
                if (eventindice.getData("name").equals((String) params[0])) {
                    newEvent.setData("name", name);
                    newEvent.setData("date", date);
                    newEvent.setData("description", description);
                    newEvent.setData("location", location);
                    eventHashMap.put(id, newEvent);
                }
            }

            eventPersistence.update(eventHashMap);
        } else {
            System.out.println("Você não pode alterar este evento");
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

    public void create(Object... params) {
        if (params.length != 5) {
            System.out.println("Só pode ter 5 parametros");
        }
        String name = (String) params[0];
        String date = (String) params[1];
        String description = (String) params[2];
        String location = (String) params[3];
        String idOwner = (String) params[4];
        Persistence event = (Persistence) new Event();


        try {
            for (Map.Entry<String, Persistence> entry : this.eventHashMap.entrySet()) {
                Persistence eventindice = entry.getValue();
                if (eventindice.getData("name").equals(name)) {
                    throw new IOException();
                }
            }
            if (isValidDate(date)){
                event.create(name, date, description, location, idOwner);
            } else {
                throw new IllegalArgumentException("Data inválida: " + date);
            }
        } catch (IOException exception) {
            System.out.println("Nome em uso");
        }
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


    private boolean isValidDate(String dateString) {
        String regex = "^\\d{2}[^\\d]*\\d{2}[^\\d]*\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateString);
        return matcher.matches();
    }
}