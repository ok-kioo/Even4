package br.upe.controller;

import br.upe.persistence.Persistence;
import br.upe.persistence.User;

public class UserController implements Controller {

    @Override
    public void create(Object... params) {
        if (params.length < 2) {
            //erro
        }
        String email = (String) params[0];
        String name = (String) params[1];
        Persistence user = new User();
        user.create(email, name);
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public void read() {

    }
    
}
