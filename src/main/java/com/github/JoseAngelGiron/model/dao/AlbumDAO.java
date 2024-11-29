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



public class AlbumDAO extends Album implements DAO<Album, String> {

    private final static String INSERT = "INSERT INTO ALBUM (Name, Photo, IDArtist, YearOfRelease) VALUES (?,?,?,?)";

    private final static String FINDALBUMBYARTIST = "SELECT A.* FROM ALBUM A " +
            "JOIN ARTIST Ar ON Ar.IDArtist = A.IDArtist " +
            "WHERE Ar.IDArtist = ? AND A.Name = ?";

    private final static String FINDALBUMSANDSONGSBYARTIST = "SELECT A.* FROM ALBUM A " +
            "WHERE A.IDArtist=?";


    private static Connection connection;
    public AlbumDAO(){
        connection = ConnectionMariaDB.getConnection();
    }

    public AlbumDAO(Album album) {
        super(album.getName(), album.getImage(), album.getYear(), album.getArtist());
        connection = ConnectionMariaDB.getConnection();
    }

    @Override
    public Album save(Album entity) {
        return null;
    }

    @Override
    public Album delete(Album entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean insert() {
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
    public Album findById(int key) {


        return null;
    }

    public List<Album> findByArtist(Artist artist){
        List<Album> listOfAlbums = new ArrayList<>();


        if(artist!=null){

            try(PreparedStatement statement = connection.prepareStatement(FINDALBUMSANDSONGSBYARTIST)) {
                statement.setInt(1, artist.getId());
                ResultSet res = statement.executeQuery();

                while(res.next()){
                    Album albumToAdd = new AlbumLazy(artist.getId());

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

    public static Album findByArtistNameAndAlbumId(Artist artist) {
        connection = ConnectionMariaDB.getConnection();
        Album albumToReturn = new Album();

        if(artist!=null) {

            try (PreparedStatement statement = connection.prepareStatement(FINDALBUMBYARTIST)) {
                statement.setInt(1, artist.getId());
                statement.setString(2, artist.getName());


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

    class AlbumLazy extends Album {
        public AlbumLazy(int id) {
            super(id);
        }

        @Override
        public List<Song> getSongsOfAlbum() {
            if (super.getSongsOfAlbum() == null) {
                setSongsOfAlbum(SongDAO.build().findSongsById(id));
            }
            return super.getSongsOfAlbum();
        }
    }
}
