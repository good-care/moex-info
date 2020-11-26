package com.mokhovav.goodcare_moex_info.database;

public interface SQLService<T> {
    int save(T object);
    Object update(T object);
    boolean delete(T object);
    T getById(int id, Class<T> c);
    T findObject(String text);
}
