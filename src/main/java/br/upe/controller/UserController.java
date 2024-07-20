package br.upe.controller;
import br.upe.persistence.Persistence;
import br.upe.persistence.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private HashMap<String, User> userHashMap;
    private User userLog;

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
    public String getData(String dataToGet) {
        String data = "";
        try {
            if (dataToGet.equals("email")) {
                System.out.println();
                data = this.userLog.getEmail();
            } else if (dataToGet.equals("cpf")) {
                data = this.userLog.getCpf();
            } else if (dataToGet.equals("id")) {
                data = this.userLog.getId();
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Data don't exist or is restrict");
        }
        return data;
    }

    private void setUserLog(User userLog) {
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
            for (Map.Entry<String, User> entry : this.userHashMap.entrySet()) {
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
        User user = userHashMap.get(this.userLog.getId());
        if (user == null) {
            System.out.println("Usuário não encontrado");
            return;
        }
        user.setEmail(email);
        user.setCpf(cpf);
        userHashMap.put(this.userLog.getId(), user);

        userPersistence.update(userHashMap);
    }

    @Override
    public void read() {

    }

    public boolean loginValidate(String email, String cpf) {
        for (Map.Entry<String, User> entry : this.userHashMap.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email) && user.getCpf().equals(cpf)) {
                setUserLog(user);
                return true;
            }
        }
        return false;
    }


}
