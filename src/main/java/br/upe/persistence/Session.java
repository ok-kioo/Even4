package br.upe.persistence;

import java.time.LocalDateTime;
import java.util.List;

public class Session {
    private int id;
    private String nome;
    private LocalDateTime date;
    private String location;
    private List<Attendee> ateendeeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Attendee> getAteendeeList() {
        return ateendeeList;
    }

    public void setAteendeeList(List<Attendee> ateendeeList) {
        this.ateendeeList = ateendeeList;
    }

    public void createSession() {

    }

    public void updateSession() {

    }

    public void deleteSession() {

    }

    public void readSession() {

    }
}
