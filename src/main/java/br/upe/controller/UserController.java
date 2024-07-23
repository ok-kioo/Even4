package br.upe.controller;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private HashMap<String, Persistence> userHashMap;
    private Persistence userLog;

    public UserController() {
        Persistence userPersistence = new User();
        this.userHashMap = userPersistence.read();
    }

    public HashMap<String, Persistence> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<String, Persistence> userHashMap) {
        this.userHashMap = userHashMap;
    }

    @Override
    public String getData(String dataToGet) {
        String data = "";
        try {
            switch (dataToGet) {
                case "email" -> {
                    System.out.println();
                    data = this.userLog.getData("email");
                }
                case "cpf" -> data = this.userLog.getData("cpf");
                case "id" -> data = this.userLog.getData("id");
                default -> throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Data don't exist or is restrict");
        }
        return data;
    }

    private void setUserLog(Persistence userLog) {
        this.userLog = userLog;
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
            for (Map.Entry<String, Persistence> entry : this.userHashMap.entrySet()) {
                Persistence user = entry.getValue();
                if (user.getData("email").equals(email)) {
                    throw new IOException();
                }
            }

            userPersistence.create(email, cpf);

        } catch (IOException exception) {
            System.out.println("Email already signed");
        }
    }

    @Override
    public void deleteById(String id) {
        Persistence userPersistence = new User();
        userHashMap.remove(id);
        userPersistence.delete(userHashMap);
    }

    @Override
    public void update(Object... params) {
        if (params.length < 2) {
            System.out.println("Só pode ter 2 parametros");
        }
        Persistence userPersistence = new User();
        String email = (String) params[0];
        String cpf = (String) params[1];
        Persistence user = userHashMap.get(this.userLog.getData("id"));
        if (user == null) {
            System.out.println("Usuário não encontrado");
            return;
        }
        user.setData("email",email);
        user.setData("cpf", cpf);
        userHashMap.put(this.userLog.getData("id"), user);

        userPersistence.update(userHashMap);
    }

    @Override
    public void read() {

    }

    public boolean loginValidate(String email, String cpf) {
        for (Map.Entry<String, Persistence> entry : this.userHashMap.entrySet()) {
            Persistence user = entry.getValue();
            if (user.getData("email").equals(email) && user.getData("cpf").equals(cpf)) {
                setUserLog(user);
                return true;
            }
        }
        return false;
    }


}
