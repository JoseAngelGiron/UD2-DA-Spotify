package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO extends Song implements DAO<Song, String> {

    private final static String FINDSONGBYALBUM = "SELECT S.* FROM SONG S " +
            "WHERE S.IDAlbum = ?";


    private static Connection connection;
    @Override
    public Song save(Song entity) {
        return null;
    }

    @Override
    public Song delete(Song entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean insert() {
        return false;
    }

    @Override
    public Song findById(int key) {








        return null;
    }

    public List<Song> findSongsById(int key) {

        List<Song> songsToReturn = new ArrayList<>();
        if (key != 0) {
            try (PreparedStatement statement = ConnectionMariaDB.getConnection().prepareStatement(FINDSONGBYALBUM)) {
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    Song songToBeAdded = new Song();
                    songToBeAdded.setId(res.getInt("IDSong"));
                    songToBeAdded.setName(res.getString("Name"));
                    songToBeAdded.setLyrics(res.getString("Lyrics"));
                    songToBeAdded.setSongFile(res.getBytes("SongFile"));
                    songToBeAdded.setMusicalGender(res.getString("Gender"));
                    songToBeAdded.setMusicalGender(res.getString("IDAlbum"));

                    songsToReturn.add(songToBeAdded);

                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return songsToReturn;

    }

    @Override
    public List<Song> findAll() {
        return List.of();
    }


    /**
     * Builds and returns a new instance of the SongDAO class.
     *
     * @return A new instance of the GameDAO class.
     */
    public static SongDAO build() {
        return new SongDAO();
    }

    @Override
    public void close() throws IOException {

    }
}
