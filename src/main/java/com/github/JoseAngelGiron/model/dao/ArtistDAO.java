package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.github.JoseAngelGiron.utils.Utils.booleanToInt;
import static com.github.JoseAngelGiron.utils.Utils.intToBoolean;


public class ArtistDAO extends Artist implements DAO <Artist, String> {

    private final static String FINDBYID = "SELECT * FROM ARTIST WHERE IDArtist=?";



    private final static String FINDARTISTANDUSERBYID = "SELECT A.*, U.* FROM ARTIST A " +
            "JOIN USER U ON U.IDUser=A.IDArtist WHERE IDArtist=? AND IDUSER=?";

    private final static String INSERT = "INSERT INTO ARTIST (IDArtist, MusicalGender, Verified) VALUES (?,?,?)";



    private static Connection connection;

    public ArtistDAO(Artist artist) {
        this.id = artist.getId();
        this.musicalGender = artist.getMusicalGender();
        this.verified = artist.isVerified();
        connection = ConnectionMariaDB.getConnection();
    }

    @Override
    public Artist save(Artist entity) {
        return null;
    }

    @Override
    public Artist delete(Artist entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean insert() {
        boolean inserted = false;

        if(this.id != 0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)){

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, musicalGender);
                preparedStatement.setInt(3, booleanToInt(isVerified()));

                preparedStatement.executeQuery();

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    inserted = true;
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    @Override
    public Artist findById(int key) {
        Artist artistToSearch = new Artist();
        if(key!=0) {

            try(PreparedStatement statement = connection.prepareStatement(FINDBYID)){
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();

                if (res.next()){
                    artistToSearch.setId(res.getInt("IDArtist"));
                    artistToSearch.setMusicalGender(res.getString("MusicalGender"));
                    artistToSearch.setVerified(intToBoolean(res.getInt("Verified")));

                    return artistToSearch;
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return artistToSearch;
    }


    public static Artist findArtistById(int key) {
        Artist artistToReturn = new Artist();

        if(key!=0){

            try(PreparedStatement preparedStatement = connection.prepareStatement(FINDARTISTANDUSERBYID)){

                preparedStatement.setInt(1, key);
                preparedStatement.setInt(2, key);

                ResultSet res = preparedStatement.executeQuery();
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
                    artistToReturn.setVerified(intToBoolean(res.getInt("IsVerified")));

                    return artistToReturn;
                }

            } catch (SQLException e) {
                System.out.println("Error "+e);
            }
        }

        return artistToReturn;
    }


    @Override
    public List<Artist> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
