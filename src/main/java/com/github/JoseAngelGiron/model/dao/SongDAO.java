package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SongDAO extends Song implements DAO<Song, String> {




    private Connection connection;
    @Override
    public Song save(Song entity) {
        return null;
    }

    @Override
    public Song delete(Song entity) throws SQLException {
        return null;
    }

    @Override
    public Song findById(String key) {
        return null;
    }

    @Override
    public List<Song> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
