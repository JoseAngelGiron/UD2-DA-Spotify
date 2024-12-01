package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.ListSongs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListSongsDAO extends ListSongs implements DAO<ListSongs, String> {



    private Connection connection;
    @Override
    public void save() {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public ListSongs findById(int key) {
        return null;
    }

    @Override
    public List<ListSongs> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
