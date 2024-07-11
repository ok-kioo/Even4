package br.upe.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SubEvent extends Event {
    private int id;
    private String name;
    private String description;
    private LocalDate date;
    private String location;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    public void createSubEvent() {

    }

    public void readSubEvent() {

    }

    public void updateSubEvent() {

    }

    public void deleteSubEvent() {

    }
}
