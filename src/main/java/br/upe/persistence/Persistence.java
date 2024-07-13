package br.upe.persistence;

public interface  Persistence {
    public void create(Object... params);
    public void delete();
    public void update();
    public void read();
}
