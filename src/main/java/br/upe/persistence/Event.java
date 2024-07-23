package br.upe.persistence;
import java.io.*;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;




public class Event implements Persistence {
    private String id;
    private String name;
    private String date;
    private String description;
    private String location;
    private String ownerId;

    public String getIdOwner() {
        return ownerId;
    }

    public void setIdOwner(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String  getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {

        return location;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    private String generateId() {
        SecureRandom secureRandom = new SecureRandom();
        long timestamp = Instant.now().toEpochMilli();
        int lastThreeDigitsOfTimestamp = (int) (timestamp % 1000);
        int randomValue = secureRandom.nextInt(900) + 100;
        return String.format("%03d%03d", lastThreeDigitsOfTimestamp, randomValue);
    }

    public void updateEvent() {

    }

    public void deleteEvent() {

    }

    @Override
    public void delete(Object... params) {

    }

    @Override
    public void update(Object... params) {

    }

    @Override
    public String getData(String dataToGet) {
        return "";
    }

    @Override
    public void setData(String dataToSet, String data) {

    }

}
