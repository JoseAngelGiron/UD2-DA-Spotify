package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.ListSongs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListSongsDAO extends ListSongs implements DAO<ListSongs, String> {



    private Connection connection;
    @Override
    public ListSongs save(ListSongs entity) {
        return null;
    }

    @Override
    public ListSongs delete(ListSongs entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean insert() {
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
