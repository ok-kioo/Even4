package br.upe.persistence;

import java.util.HashMap;

public interface  Persistence {
    void create(Object... params);
    void delete();
    void update();
    HashMap<String, User> read();
}
