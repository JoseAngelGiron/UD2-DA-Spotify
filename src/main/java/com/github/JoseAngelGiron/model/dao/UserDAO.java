package com.github.JoseAngelGiron.model.dao;


import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.JoseAngelGiron.utils.Utils.intToBoolean;


public class UserDAO extends User implements DAO<User, String> {

    /** USERQUERYS **/
    private static final String FINDUSERANDARTISTRBYEMAIL = "SELECT U.*, A.*, AD.* FROM USER U " +
                    "LEFT JOIN ARTIST A ON U.IDUser = A.IDArtist " +
                    "LEFT JOIN ADMIN AD ON U.IDUser = AD.IDAdmin "+
                    "WHERE U.Email = ?";


    private static final String FINDBYNAME = "SELECT * FROM USER WHERE Nick =?";

    private static final String INSERT = "INSERT INTO USER (Nick, Password, Email) VALUES (?,?,?)";
    private static final String INSERTINTOFRIENDS = "INSERT INTO FRIENDS (IDUser, IDFriend) VALUES (?,?)";
    private final static String UPDATE = "UPDATE USER SET Nick=?, Password=?, Photo=?, Name=?, Surname=? ,DNI=?, Adress=? WHERE IDUser=?;";
    private final static String DELETE= "DELETE FROM USER WHERE IDuser=?";

    /** FRIENDQUERYS **/
    private static final String FINDKEYSINFRIENDS = "SELECT IDUser, IDFriend FROM FRIENDS WHERE IDUser=? AND IDFriend=?";

    private static final String FINDFOLLOWERS = "SELECT U.* FROM USER U " +
            "WHERE U.IDUser IN ( " +
            "SELECT F.IDUser FROM FRIENDS F " +
            "WHERE F.IDFriend = ? " +
            ")";
    private static final String DELETEFOLLOW = "DELETE FROM FRIENDS WHERE IDUser = ? AND IDFriend = ?";


    /** SEARCHQUERYS **/
    private static final String INSERTINTOSEARCH = "INSERT INTO SEARCH (IDUser, IDSong, Date) VALUES (?,?,?)";

    private static final String DELETESEARCHS = "DELETE FROM SEARCH WHERE IDUser = ?";

    private static final String FINDLASTSEARCHS = "SELECT S.* " +
            "FROM SEARCH Se " +
            "JOIN SONG S ON Se.IDSong = S.IDSong " +
            "WHERE Se.IDUser = ? " +
            "GROUP BY S.IDSong " +
            "ORDER BY MAX(Se.Date) DESC " +
            "LIMIT 5";






    private static Connection connection ;

    public UserDAO() {
        connection = ConnectionMariaDB.getConnection();

    }

    public UserDAO(String name, String password, String email) {
        super(name, password, email);
        connection = ConnectionMariaDB.getConnection();


    }

    public UserDAO(User user) {
        super(user.getId(), user.getName(), user.getPassword(), user.getPhoto(),
                user.getUserName(), user.getSurname(), user.getEmail(),
                user.getDni(), user.getAddress());
        connection = ConnectionMariaDB.getConnection();
    }

    @Override
    public void save() {

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
        if(name != null && password != null && email != null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);

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
        boolean updated =false;
        if(id>0){
            try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setBytes(3, photo);
                preparedStatement.setString(4, userName);
                preparedStatement.setString(5, surname);
                preparedStatement.setString(6, dni);
                preparedStatement.setString(7, address);

                preparedStatement.setInt(8, id);


                preparedStatement.executeQuery();
                updated = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return updated;
    }

    @Override
    public User findById(int key) {
        return null;
    }

    public List<User> findByName(String name){
        List<User> usersList = new ArrayList<>();


        if(name!=null){

            try(PreparedStatement statement = connection.prepareStatement(FINDBYNAME)) {
                statement.setString(1, name);


                ResultSet res = statement.executeQuery();

                while(res.next()){
                    User userToReturn = new User();

                    userToReturn.setId(res.getInt("IDUser"));
                    userToReturn.setName(res.getString("Nick"));
                    userToReturn.setPassword(res.getString("Password"));
                    userToReturn.setPhoto(res.getBytes("Photo"));
                    userToReturn.setUserName(res.getString("Name"));
                    userToReturn.setSurname(res.getString("Surname"));
                    userToReturn.setEmail(res.getString("Email"));
                    userToReturn.setDni(res.getString("DNI"));
                    userToReturn.setAddress(res.getString("Adress"));

                    usersList.add(userToReturn);

                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return usersList;
    }

    public static User findByEmail(String email) {
        User userToReturn = new User();
        connection = ConnectionMariaDB.getConnection();
        if(email!=null){

            try(PreparedStatement statement = connection.prepareStatement(FINDUSERANDARTISTRBYEMAIL)) {
                statement.setString(1, email);


                ResultSet res = statement.executeQuery();

                if(res.next()){
                    userToReturn.setId(res.getInt("IDUser"));
                    userToReturn.setName(res.getString("Nick"));
                    userToReturn.setPassword(res.getString("Password"));
                    userToReturn.setPhoto(res.getBytes("Photo"));
                    userToReturn.setUserName(res.getString("Name"));
                    userToReturn.setSurname(res.getString("Surname"));
                    userToReturn.setEmail(res.getString("Email"));
                    userToReturn.setDni(res.getString("DNI"));
                    userToReturn.setAddress(res.getString("Adress"));

                    if(res.getInt("IDArtist") != 0){
                        return new Artist(userToReturn, res.getString("MusicalGender"),
                                intToBoolean(res.getInt("Verified")));

                    } else if (res.getInt("IDAdmin") != 0) {
                        return new Admin(userToReturn,
                                intToBoolean(res.getInt("ISAdmin")));
                    }

                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return userToReturn;

    }



    public static UserDAO build(){
        return new UserDAO();
    }

    @Override
    public List findAll() {
        return List.of();
    }


    public static List<User> findFollowers(int id){
        connection = ConnectionMariaDB.getConnection();

        List<User> usersList = new ArrayList<>();


        if(id>0){

            try(PreparedStatement statement = connection.prepareStatement(FINDFOLLOWERS)) {
                statement.setInt(1, id);


                ResultSet res = statement.executeQuery();

                while(res.next()){
                    User userToReturn = new User();

                    userToReturn.setId(res.getInt("U.IDUser"));
                    userToReturn.setName(res.getString("Nick"));
                    userToReturn.setPassword(res.getString("Password"));
                    userToReturn.setPhoto(res.getBytes("Photo"));
                    userToReturn.setUserName(res.getString("Name"));
                    userToReturn.setSurname(res.getString("Surname"));
                    userToReturn.setEmail(res.getString("Email"));
                    userToReturn.setDni(res.getString("DNI"));
                    userToReturn.setAddress(res.getString("Adress"));

                    usersList.add(userToReturn);

                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return usersList;

    }

    public static boolean insertIntoFriends(int keyUser, int keyFollower){
        connection = ConnectionMariaDB.getConnection();
        boolean inserted = false;
        if (!findIfAlreadyFriends(keyUser, keyFollower)){
            if( keyUser != keyFollower){
                try(PreparedStatement preparedStatement = connection.prepareStatement(INSERTINTOFRIENDS)) {

                    preparedStatement.setInt(1, keyUser);
                    preparedStatement.setInt(2, keyFollower);


                    preparedStatement.executeQuery();
                    inserted = true;
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return inserted;
    }


    public static void deleteFollow(int idUser, int idFriend) throws SQLException {
        connection = ConnectionMariaDB.getConnection();
        if(idUser>0 && idFriend>0){
            try(PreparedStatement statement = ConnectionMariaDB.getConnection().prepareStatement(DELETEFOLLOW)){

                statement.setInt(1, idUser);
                statement.setInt(2, idFriend);
                statement.executeUpdate();
            }
        }


    }

    public static boolean findIfAlreadyFriends(int keyUser, int keyFriend){
        connection = ConnectionMariaDB.getConnection();
        boolean friends = false;
        if(keyUser>0 && keyFriend>0){

            try(PreparedStatement statement = connection.prepareStatement(FINDKEYSINFRIENDS)) {
                statement.setInt(1, keyUser);
                statement.setInt(2, keyFriend);


                ResultSet res = statement.executeQuery();
                if(res.next()){
                    friends = true;
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return friends;
    }



    public static boolean insertIntoSearch(int keyUser, int keySong){
        connection = ConnectionMariaDB.getConnection();
        boolean inserted = false;

            if( keyUser >0 && keySong>0){
                try(PreparedStatement preparedStatement = connection.prepareStatement(INSERTINTOSEARCH)) {

                    preparedStatement.setInt(1, keyUser);
                    preparedStatement.setInt(2, keySong);
                    LocalDateTime now = LocalDateTime.now();
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(now));


                    preparedStatement.executeQuery();
                    inserted = true;
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


        return inserted;
    }

    public static void deleteSearchs(int userKey){
        connection = ConnectionMariaDB.getConnection();
        if(userKey>0){
            try(PreparedStatement statement = ConnectionMariaDB.getConnection().prepareStatement(DELETESEARCHS)){

                statement.setInt(1, userKey);

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Song> findLastSongsSearched(int userKey){
        List<Song> songList = new ArrayList<>();
        connection = ConnectionMariaDB.getConnection();

        if(userKey>0){
            try(PreparedStatement statement = connection.prepareStatement(FINDLASTSEARCHS)) {
                statement.setInt(1, userKey);


                ResultSet res = statement.executeQuery();

                while(res.next()){
                    Song songToBeAdded = new Song();
                    songToBeAdded.setId(res.getInt("IDSong"));
                    songToBeAdded.setName(res.getString("Name"));
                    songToBeAdded.setPhotoSong(res.getBytes("PhotoSong"));
                    songToBeAdded.setSongFile(res.getBytes("SongFile"));
                    songToBeAdded.setMusicalGender(res.getString("Gender"));

                    songList.add(songToBeAdded);

                }

            }catch (SQLException e){
                e.printStackTrace();
            }


        }

        return songList;
    }


    @Override
    public void close() throws IOException {

    }


}
