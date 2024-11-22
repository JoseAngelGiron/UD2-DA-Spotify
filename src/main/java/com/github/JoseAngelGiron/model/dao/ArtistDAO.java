package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Artist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ArtistDAO extends Artist implements DAO <Artist, String> {



    private Connection connection;

    @Override
    public Artist save(Artist entity) {
        return null;
    }

    @Override
    public Artist delete(Artist entity) throws SQLException {
        return null;
    }

    @Override
    public Artist findById(String key) {
        return null;
    }

    @Override
    public List<Artist> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
