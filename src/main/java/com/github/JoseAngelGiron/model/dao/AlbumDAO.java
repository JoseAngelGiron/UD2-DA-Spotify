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


public class AlbumDAO extends Album implements DAO<Album, String> {

    private final static String INSERT = "INSERT INTO ALBUM (Name, Photo, IDArtist, YearOfRelease) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE ALBUM SET Name=?, Photo=?, YearOfRelease=? WHERE IDAlbum=?;";
    private final static String DELETE = "DELETE FROM ALBUM WHERE IDAlbum=?";

    private final static String FINDALBUMBYARTISTID = "SELECT A.* FROM ALBUM A WHERE A.IDArtist = ?";

    private final static String FINDALBUMBYARTIST = "SELECT A.* FROM ALBUM A " +
            "JOIN ARTIST Ar ON Ar.IDArtist = A.IDArtist " +
            "WHERE Ar.IDArtist = ? AND A.Name = ?";

    private final static String FINDARTISTBYALBUM = "SELECT Ar.*, U.* FROM ARTIST Ar " +
            "JOIN ALBUM A ON A.IDArtist = Ar.IDArtist " +
            "JOIN USER U ON U.IDUser = Ar.IDArtist "+
            "WHERE IDAlbum = ?";


    private final static String FINDALBUMSANDSONGSBYARTIST = "SELECT A.* FROM ALBUM A " +
            "WHERE A.IDArtist=?";


    private static Connection connection;
    public AlbumDAO(){
        connection = ConnectionMariaDB.getConnection();
    }

    public AlbumDAO(Album album) {
        super(album.getId() ,album.getName(), album.getImage(), album.getYear(), album.getArtist());
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
        boolean inserted = false;

        if(name != null && image!=null  && year != 0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setString(1, name);
                preparedStatement.setBytes(2, image);
                preparedStatement.setInt(3, artist.getId());
                preparedStatement.setInt(4, year);

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

        if(name != null && image!=null  && year != 0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

                preparedStatement.setString(1, name);
                preparedStatement.setBytes(2, image);
                preparedStatement.setInt(3, year);

                preparedStatement.setInt(4, id);


                preparedStatement.executeQuery();
                updated = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return updated;
    }

    @Override
    public Album findById(int key) {

        return null;
    }




    public List<Album> findListOfAlbumByArtistID(int key){
        List<Album> albumListToReturn = new ArrayList<>();
        if(key!=0) {

            try(PreparedStatement statement = connection.prepareStatement(FINDALBUMBYARTISTID)){
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();

                while (res.next()){
                    Album albumToAdd = new Album();
                    albumToAdd.setId(res.getInt("IDAlbum"));
                    albumToAdd.setName(res.getString("Name"));
                    albumToAdd.setImage(res.getBytes("Photo"));
                    albumToAdd.setYear(res.getInt("YearOfRelease"));

                    albumListToReturn.add(albumToAdd);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return albumListToReturn;
    }

    public List<Album> findAlbumsAndSongs(Artist artist){
        List<Album> listOfAlbums = new ArrayList<>();


        if(artist!=null){

            try(PreparedStatement statement = connection.prepareStatement(FINDALBUMSANDSONGSBYARTIST)) {
                statement.setInt(1, artist.getId());
                ResultSet res = statement.executeQuery();

                while(res.next()){
                    Album albumToAdd = new AlbumEager(artist.getId());

                    albumToAdd.setId(res.getInt("IDAlbum"));
                    albumToAdd.setName(res.getString("Name"));
                    albumToAdd.setImage(res.getBytes("Photo"));
                    albumToAdd.setArtist(artist); //por si lo necesito
                    albumToAdd.setYear(res.getInt("YearOfRelease"));

                    listOfAlbums.add(albumToAdd);


                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }


        return listOfAlbums;
    }

    public static Artist findArtistByAlbumID(int key){

        Artist artistToReturn = new Artist();
        connection = ConnectionMariaDB.getConnection();

        if(key>0){

            try(PreparedStatement statement = connection.prepareStatement(FINDARTISTBYALBUM)) {
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();

                if(res.next()){

                    artistToReturn.setId(res.getInt("IDArtist"));
                    artistToReturn.setName(res.getString("Nick"));
                    artistToReturn.setPassword(res.getString("Password"));
                    artistToReturn.setPhoto(res.getBytes("Photo"));
                    artistToReturn.setName(res.getString("Name"));
                    artistToReturn.setSurname(res.getString("Surname"));
                    artistToReturn.setEmail(res.getString("Email"));
                    artistToReturn.setDni(res.getString("DNI"));
                    artistToReturn.setAddress(res.getString("Adress"));
                    artistToReturn.setMusicalGender(res.getString("MusicalGender"));
                    artistToReturn.setVerified(intToBoolean(res.getInt("Verified")));

                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        return artistToReturn;
    }

    public static Album findByArtistNameAndAlbumId(Artist artist, Album album) {
        connection = ConnectionMariaDB.getConnection();
        Album albumToReturn = new Album();

        if(artist!=null) {

            try (PreparedStatement statement = connection.prepareStatement(FINDALBUMBYARTIST)) {
                statement.setInt(1, artist.getId());
                statement.setString(2, album.getName());


                ResultSet res = statement.executeQuery();

                if (res.next()) {
                    albumToReturn.setId(res.getInt("IDAlbum"));
                    albumToReturn.setName(res.getString("Name"));
                    albumToReturn.setImage(res.getBytes("Photo"));
                    albumToReturn.setArtist(artist);
                    albumToReturn.setYear(res.getInt("YearOfRelease"));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return albumToReturn;
    }

    @Override
    public List<Album> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }

    class AlbumEager extends Album {
        public AlbumEager(int id) {
            super(id);
        }

        @Override
        public List<Song> getSongsOfAlbum() {
            if (super.getSongsOfAlbum() == null) {
                setSongsOfAlbum(SongDAO.build().findSongsByAlbumId(id));
            }
            return super.getSongsOfAlbum();
        }
    }
}
