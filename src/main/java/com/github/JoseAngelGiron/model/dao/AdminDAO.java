package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminDAO extends Admin implements DAO<Admin, String> {

    private static final String INSERT = "INSERT INTO ADMIN (IDAdmin, ISAdmin) VALUES (?,?)";
    private final static String UPDATE = "UPDATE ADMIN SET ISAdmin=? WHERE IDAdmin=?;";
    private final static String DELETE= "DELETE FROM ADMIN WHERE IDAdmin=?";

    private Connection connection;

    @Override
    public void save() {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Admin findById(int key) {
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
