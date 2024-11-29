package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Album;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AlbumDAO extends Album implements DAO<Album, String> {

    private final static String INSERT = "INSERT INTO ALBUM (Name, Photo, IDArtist, YearOfRelease) VALUES (?,?,?,?)";

    private static Connection connection;

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
        if(image == null){
            System.out.println("Mal");
        }
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

    @Override
    public List<Album> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
