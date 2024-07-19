package br.upe.controller;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private HashMap<String, User> userHashMap;

    public UserController() {
        Persistence userPersistence = new User();
        this.userHashMap = userPersistence.read();
    }

    public HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<String, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    @Override
    public void create(Object... params) {
        if (params.length < 2) {
            System.out.println("Só pode ter 2 parametros");
        }

        String email = (String) params[0];
        String cpf = (String) params[1];
        Persistence userPersistence = new User();
        try {
            this.userHashMap = userPersistence.read();

            for (Map.Entry<String, User> entry : userHashMap.entrySet()) {
                User user = entry.getValue();
                if (user.getEmail().equals(email)) {
                    throw new IOException();
                }
            }

            userPersistence.create(email, cpf);

        } catch (IOException exception) {
            System.out.println("Email already signed");
        }


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

    public boolean loginValidate(String email, String cpf) {
        for (Map.Entry<String, User> entry : this.userHashMap.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email) && user.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

}
