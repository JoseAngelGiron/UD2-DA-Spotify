package com.github.JoseAngelGiron.model.dao;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T,K> extends Closeable {
    void save();
    void delete() throws SQLException;
    boolean insert();
    boolean update();
    T findById(int key);
    List<T> findAll();
}
