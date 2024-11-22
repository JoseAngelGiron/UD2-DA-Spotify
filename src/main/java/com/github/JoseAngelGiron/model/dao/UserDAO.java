package com.github.JoseAngelGiron.model.dao;


import com.github.JoseAngelGiron.model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDAO extends User implements DAO<User, String> {

    private final static String FINDBYNICKANDPASSWORD ="SELECT * FROM USER WHERE E-mail = ? AND Password= ?";


    private Connection connection;


    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User delete(User entity) throws SQLException {
        return null;
    }

    @Override
    public User findById(String key) {
        return null;
    }

    public void findByUserAndPassword(String email, String password){

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
                    this.email = res.getString("E-mail");
                    this.dni = res.getString("DNI");
                    this.address = res.getString("Adress");

                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
