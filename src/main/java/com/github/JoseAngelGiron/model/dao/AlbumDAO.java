package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Album;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AlbumDAO extends Album implements DAO<Album, String> {

    private Connection connection;
    @Override
    public Album save(Album entity) {
        return null;
    }

    @Override
    public Album delete(Album entity) throws SQLException {
        return null;
    }

    @Override
    public void insert() {

    }

    @Override
    public Album findById(String key) {
        return null;
    }

    @Override
    public List<Album> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
