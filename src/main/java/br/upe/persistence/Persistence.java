package br.upe.persistence;

import java.util.HashMap;

public interface  Persistence {
    void create(Object... params);
    void delete(Object... params);
    void update(Object... params);
    HashMap<String, User> read();
}
