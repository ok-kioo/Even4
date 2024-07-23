package br.upe.controller;

import br.upe.persistence.User;

public interface Controller {
    void create(Object... params);
    void update(Object... params);
    void read();
    void delete(Object... params);
    void list(String idowner);

    boolean loginValidate(String email, String cpf);
    String getData(String dataToGet);


}
