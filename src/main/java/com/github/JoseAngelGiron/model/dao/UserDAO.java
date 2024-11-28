package com.github.JoseAngelGiron.model.dao;


import com.github.JoseAngelGiron.model.connection.ConnectionMariaDB;
import com.github.JoseAngelGiron.model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDAO extends User implements DAO<User, String> {

    private final static String FINDBYNICKANDPASSWORD ="SELECT * FROM USER WHERE Email = ? AND Password= ?";
    private final static String FINDBYEMAIL = "SELECT * FROM USER WHERE Email = ? ";
    private final static String INSERT = "INSERT INTO USER (Nick, Password, Email) VALUES (?,?,?)";


    private Connection connection;

    public UserDAO() {
        this.connection = ConnectionMariaDB.getConnection();
    }

    public UserDAO(String name, String password, String email) {
        super(name, password, email);
        connection = ConnectionMariaDB.getConnection();

    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User delete(User entity) throws SQLException {
        return null;
    }

    @Override
    public void insert() {

        if(name != null && password != null && email != null){
            try(PreparedStatement statement = connection.prepareStatement(INSERT)) {

                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, email);

                statement.executeQuery();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public User findById(String key) {
        return null;
    }
    
    

    public User findByEmailAndPassword(String email, String password){

        if(email != null && password != null){

            try(PreparedStatement statement = connection.prepareStatement(FINDBYNICKANDPASSWORD)) {
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet res = statement.executeQuery();

                if(res.next()){
                    this.id = res.getInt("IDUser");
                    this.name = res.getString("Nick");

                    this.password = res.getString("Password");
                    this.photo = res.getString("URLPhoto");
                    this.userName = res.getString("Name");
                    this.surname = res.getString("Surname");
                    this.email = res.getString("Email");
                    this.dni = res.getString("DNI");
                    this.address = res.getString("Adress");
                    return this;
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        return this;
        
    }

    public User findByEmail(String email) {

        if(email!=null){

            try(PreparedStatement statement = connection.prepareStatement(FINDBYEMAIL)) {
                statement.setString(1, email);


                ResultSet res = statement.executeQuery();

                if(res.next()){
                    this.id = res.getInt("IDUser");
                    this.name = res.getString("Nick");

                    this.password = res.getString("Password");
                    this.photo = res.getString("URLPhoto");
                    this.userName = res.getString("Name");
                    this.surname = res.getString("Surname");
                    this.email = res.getString("Email");
                    this.dni = res.getString("DNI");
                    this.address = res.getString("Adress");
                    return this;
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return this;

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
