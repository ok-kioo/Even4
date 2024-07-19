package br.upe.controller;

public interface Controller {
    void create(Object... params);
    void delete();
    void update();
    void read();

    boolean loginValidate(String email, String cpf);
}
