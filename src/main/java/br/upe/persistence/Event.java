package br.upe.persistence;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private LocalDate date;
    private String description;
    private String location;

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {

        return location;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setDate(LocalDate date) {

        this.date = date;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void createEvent() {

    }

    public void updateEvent() {

    }

    public void deleteEvent() {

    }

    public void readEvent() {

    }
}
