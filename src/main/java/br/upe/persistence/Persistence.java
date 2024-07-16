package br.upe.persistence;

import java.util.HashMap;

public interface  Persistence {
    public void create(Object... params);
    public void delete();
    public void update();
    public void read();
}
