package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminDAO extends Admin implements DAO<Admin, String> {


    private Connection connection;

    @Override
    public Admin save(Admin entity) {
        return null;
    }

    @Override
    public Admin delete(Admin entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean insert() {
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
