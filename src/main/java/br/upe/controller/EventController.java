package br.upe.controller;
import br.upe.persistence.Event;
import br.upe.persistence.Persistence;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
    public void deleteById(String id) {


    }


    @Override
    public void update(Object... params) {


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
        return "";
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

    private boolean isValidDate(String dateString) {
        String regex = "^\\d{2}-\\d{2}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateString);
        return matcher.matches();
    }
}