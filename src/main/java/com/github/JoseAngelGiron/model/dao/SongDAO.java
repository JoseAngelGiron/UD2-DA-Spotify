package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;

import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.github.JoseAngelGiron.utils.Utils.intToBoolean;

public class SongDAO extends Song implements DAO<Song, String> {


    private final static String INSERT = "INSERT INTO SONG (Name, PhotoSong, SongFile, Gender, IDAlbum) VALUES (?,?,?,?,?)";
    private final static String UPDATE = "UPDATE SONG SET Name=?, PhotoSong=?, SongFile=?, Gender=? WHERE IDSong=?;";
    private final static String DELETE= "DELETE FROM SONG WHERE IDSong=?";

    private final static String FINDSONGSBYALBUM = "SELECT S.* FROM SONG S WHERE S.IDAlbum = ?";

    private final static String FINDSONGALBUMANDARTIST = "SELECT S.*,A.*,Ar.*,U.* FROM SONG S " +
            "JOIN Album A ON S.IDAlbum = A.IDAlbum " +
            "JOIN Artist Ar ON A.IDArtist = Ar.IDArtist " +
            "JOIN User u ON A.IDArtist = U.IDuser "+
            "WHERE S.Name = ? "+
            "ORDER BY S.NumberOfPlays "+
            "LIMIT 5;";


    private static Connection connection;

    public SongDAO() {
    }



    public SongDAO(Song song) {
        super(song.getId(), song.getName(), song.getSongFile(), song.getPhotoSong(),  song.getMusicalGender(), song.getAlbum());
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
    public void delete() throws SQLException {

        if(id>0){
            try(PreparedStatement statement = ConnectionMariaDB.getConnection().prepareStatement(DELETE)){

                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }

    }

    @Override
    public boolean insert() {
        boolean inserted =false;

        if(name != null && musicalGender != null && songFile != null && album.getId()>0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setString(1, name);
                preparedStatement.setBytes(2, photoSong);
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
        boolean updated = false;

        if(songFile!=null && photoSong!=null && name!=null && musicalGender !=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

                preparedStatement.setString(1, name);
                preparedStatement.setBytes(2, photoSong);
                preparedStatement.setBytes(3, songFile);
                preparedStatement.setString(4, musicalGender);

                preparedStatement.setInt(5, id);

                preparedStatement.executeQuery();
                updated = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return updated;
    }

    @Override
    public Song findById(int key) {
        return null;
    }

    public List<Song> findSongsById(int key) {

        List<Song> songsToReturn = new ArrayList<>();
        if (key != 0) {
            try (PreparedStatement statement = ConnectionMariaDB.getConnection().prepareStatement(FINDSONGSBYALBUM)) {
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    Song songToBeAdded = new Song();
                    songToBeAdded.setId(res.getInt("IDSong"));
                    songToBeAdded.setName(res.getString("Name"));
                    songToBeAdded.setPhotoSong(res.getBytes("PhotoSong"));
                    songToBeAdded.setSongFile(res.getBytes("SongFile"));
                    songToBeAdded.setMusicalGender(res.getString("Gender"));

                    songsToReturn.add(songToBeAdded);

                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return songsToReturn;
    }

    public static List<Song> findSongAlbumAndArtist(String name){
        List<Song> songList = new ArrayList<>();
        connection = ConnectionMariaDB.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FINDSONGALBUMANDARTIST)) {
            statement.setString(1, name);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Song songToBeAdded = new Song();
                songToBeAdded.setId(res.getInt("S.IDSong"));
                songToBeAdded.setName(res.getString("S.Name"));
                songToBeAdded.setPhotoSong(res.getBytes("S.PhotoSong"));
                songToBeAdded.setSongFile(res.getBytes("S.SongFile"));
                songToBeAdded.setNumberOfPlays(res.getInt("S.NumberOfPlays"));
                songToBeAdded.setMusicalGender(res.getString("S.Gender"));

                Album album = new Album();
                album.setId(res.getInt("A.IDAlbum"));
                album.setName(res.getString("A.Name"));
                album.setImage(res.getBytes("A.Photo"));
                album.setYear(res.getInt("A.YearOfRelease"));


                Artist artist = new Artist();
                artist.setId(res.getInt("Ar.IDArtist"));
                artist.setName(res.getString("Ar.MusicalGender"));
                artist.setVerified(intToBoolean(res.getInt("Ar.verified")));

                artist.setName(res.getString("U.Nick"));
                artist.setPassword(res.getString("U.Password"));
                artist.setPhoto(res.getBytes("U.Photo"));
                artist.setUserName(res.getString("U.Name"));
                artist.setSurname(res.getString("U.Surname"));
                artist.setEmail(res.getString("U.Email"));
                artist.setDni(res.getString("U.DNI"));
                artist.setAddress(res.getString("U.Adress"));

                album.setArtist(artist);
                songToBeAdded.setAlbum(album);

                songList.add(songToBeAdded);
            }

            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songList;


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
