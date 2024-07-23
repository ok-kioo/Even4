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
        Persistence eventPersistence = (Persistence) new Event();
        this.eventHashMap = eventPersistence.read();
    }


    public HashMap<String, Persistence> getEventHashMap() {
        return eventHashMap;
    }


    public void setEventHashMap(HashMap<String, Persistence> eventHashMap) {
        this.eventHashMap = eventHashMap;
    }


    @Override
    public void create(Object... params) {

    }

    @Override
    public void deleteById(String id) {


    }


    @Override
    public void update(Object... params) {


    }


    @Override
    public void read() {


    }


    @Override
    public boolean loginValidate(String email, String cpf) {
        return false;
    }


    @Override
    public String getData(String dataToGet) {
        return "";
    }


}