package br.upe.persistence;

import java.util.HashMap;

public interface  Persistence {
    void create(Object... params);
    void delete(Object... params);
    void update(Object... params);

    String getData(String dataToGet);
    void setData(String dataToSet, String data);
    HashMap<String, Persistence> read();
}
