package com.github.JoseAngelGiron.model.dao;


import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.github.JoseAngelGiron.utils.Utils.intToBoolean;


public class UserDAO extends User implements DAO<User, String> {


    private static final String FINDUSERANDARTISTRBYEMAIL = "SELECT U.*, A.*, AD.* FROM USER U " +
                    "LEFT JOIN ARTIST A ON U.IDUser = A.IDArtist " +
                    "LEFT JOIN ADMIN AD ON U.IDUser = AD.IDAdmin "+
                    "WHERE U.Email = ?";

    private static final String INSERT = "INSERT INTO USER (Nick, Password, Email) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE USER SET Nick=?, Password=?, Photo=?, Name=?, Surname=? ,DNI=?, Adress=? WHERE IDUser=?;";
    private final static String DELETE= "DELETE FROM USER WHERE IDuser=?";


    private  static Connection connection ;

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

        if(name != null && password != null && email != null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);

                preparedStatement.executeQuery();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;

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

    @Override
    public void close() throws IOException {

    }


}
