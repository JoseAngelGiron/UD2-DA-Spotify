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

    private final static String FINDSONGBYALBUM = "SELECT S.* FROM SONG S WHERE S.IDAlbum = ?";
    private final static String INSERT = "INSERT INTO SONG (Name, Lyrics, SongFile, Gender, IDAlbum) VALUES (?,?,?,?,?)";


    private static Connection connection;

    public SongDAO() {
    }

    public SongDAO(Song song) {
        super( song.getName(), song.getSongFile(),  song.getMusicalGender(), song.getAlbum());
        connection = ConnectionMariaDB.getConnection();
    }

    @Override
    public void save() {

        if(this.id>0){
            update();
        }else{
            insert();
        }

    }

    @Override
    public Song delete(Song entity) throws SQLException {
        return null;
    }

    @Override
    public boolean insert() {
        boolean inserted =false;

        if(name != null && musicalGender != null && songFile != null && album.getId()>0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lyrics);
                preparedStatement.setBytes(3, songFile);
                preparedStatement.setString(4, musicalGender);
                preparedStatement.setInt(5, album.getId());

                preparedStatement.executeQuery();
                inserted = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return inserted;


    }

    @Override
    public boolean update() {
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
