package br.upe.controller;

import br.upe.persistence.User;

public interface Controller {
    void create(Object... params);
    void deleteById(String id);
    void update(Object... params);
    void read();

    boolean loginValidate(String email, String cpf);
    String getData(String dataToGet);

}
