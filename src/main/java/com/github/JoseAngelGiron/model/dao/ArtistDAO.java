package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Artist;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.github.JoseAngelGiron.utils.Utils.booleanToInt;
import static com.github.JoseAngelGiron.utils.Utils.intToBoolean;


public class ArtistDAO extends Artist implements DAO <Artist, String> {

    private final static String INSERT = "INSERT INTO ARTIST (IDArtist, MusicalGender, Verified) VALUES (?,?,?)";

    private final static String FINDBYID = "SELECT * FROM ARTIST WHERE IDArtist=?";

    private final static String FINDARTISTANDUSERBYID = "SELECT A.*, U.* FROM ARTIST A " +
            "JOIN USER U ON U.IDUser=A.IDArtist WHERE IDArtist=? AND IDUSER=?";

    private final static String FINDAMOUNTOFPLAYS = "SELECT SUM(S.NumberOfPlays) AS TotalPlays " +
            "FROM Song S " +
            "JOIN Album A ON A.IDAlbum = S.IDAlbum " +
            "JOIN Artist Ar ON Ar.IDArtist = A.IDArtist " +
            "WHERE Ar.IDArtist = ? " +
            "GROUP BY Ar.IDArtist";

    private final static String FINDARTISTSPLAYS =
            "SELECT Ar.IDArtist, U.Nick, U.Photo, Ar.Verified, SUM(S.NumberOfPlays) AS TotalPlays " +
                    "FROM Song S " +
                    "JOIN Album A ON A.IDAlbum = S.IDAlbum " +
                    "JOIN Artist Ar ON Ar.IDArtist = A.IDArtist " +
                    "JOIN User U ON U.IDUser = Ar.IDArtist " +
                    "GROUP BY Ar.IDArtist, U.Nick, U.Photo, Ar.Verified " +
                    "ORDER BY TotalPlays DESC " +
                    "LIMIT 5";

    private final static String FINDARTISTSVERIFIED = "SELECT Ar.*, U.* " +
            "FROM ARTIST Ar " +
            "JOIN USER U ON Ar.IDArtist = U.IDUser " +
            "JOIN FRIEND F ON U.IDUser = F.IDUser " +
            "WHERE Ar.Verified = 1 AND Ar.IDArtist = ?";







    private static Connection connection;


    public ArtistDAO() {
    }

    public ArtistDAO(Artist artist) {
        this.id = artist.getId();
        this.musicalGender = artist.getMusicalGender();
        this.verified = artist.isVerified();
        connection = ConnectionMariaDB.getConnection();
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public boolean insert() {
        boolean inserted = false;

        if(this.id != 0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)){

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, musicalGender);
                preparedStatement.setInt(3, booleanToInt(verified));

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
    public boolean update() {
        return false;
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
                    artistToReturn.setVerified(intToBoolean(res.getInt("Verified")));

                    return artistToReturn;
                }

            } catch (SQLException e) {
                System.out.println("Error "+e);
            }
        }

        return artistToReturn;
    }

    public static List<Artist> findArtistsVerifiedThatFollows(int key){
        List<Artist> artistsRelatedToReturn = new ArrayList<>();
        connection = ConnectionMariaDB.getConnection();

        if(key>0){

            try(PreparedStatement statement = connection.prepareStatement(FINDARTISTSVERIFIED)){
                statement.setInt(1, key);
                ResultSet res = statement.executeQuery();

                while (res.next()){
                    Artist artistToAdd = new Artist();

                    artistToAdd.setId(res.getInt("IDArtist"));
                    artistToAdd.setMusicalGender(res.getString("MusicalGender"));
                    artistToAdd.setVerified(intToBoolean(res.getInt("Verified")));
                    artistToAdd.setName(res.getString("Nick"));
                    artistToAdd.setPassword(res.getString("Password"));
                    artistToAdd.setPhoto(res.getBytes("Photo"));
                    artistToAdd.setName(res.getString("Name"));
                    artistToAdd.setSurname(res.getString("Surname"));
                    artistToAdd.setEmail(res.getString("Email"));
                    artistToAdd.setDni(res.getString("DNI"));
                    artistToAdd.setAddress(res.getString("Adress"));
                    artistToAdd.setMusicalGender(res.getString("MusicalGender"));
                    artistToAdd.setVerified(intToBoolean(res.getInt("Verified")));

                    artistsRelatedToReturn.add(artistToAdd);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        return artistsRelatedToReturn;
    }

    public static int findAmountOfPlays(int key){

        int totalPlays = 0;
        connection = ConnectionMariaDB.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FINDAMOUNTOFPLAYS)) {

            statement.setInt(1, key);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalPlays = resultSet.getInt("TotalPlays");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return totalPlays;
    }

    public static List<Artist> findMostPopularArtists(){

        List<Artist> artistsList = new ArrayList<>();
        connection = ConnectionMariaDB.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FINDARTISTSPLAYS)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                Artist artistToAdd = new Artist();

                artistToAdd.setId(resultSet.getInt("IDArtist"));
                artistToAdd.setName(resultSet.getString("Nick"));
                artistToAdd.setVerified(intToBoolean(resultSet.getInt("Verified")));
                artistToAdd.setTotalPlays(resultSet.getInt("TotalPlays"));
                artistToAdd.setPhoto(resultSet.getBytes("Photo"));

                artistsList.add(artistToAdd);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistsList;

    }


    public static ArtistDAO build(){
        return new ArtistDAO();
    }


    @Override
    public List<Artist> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
