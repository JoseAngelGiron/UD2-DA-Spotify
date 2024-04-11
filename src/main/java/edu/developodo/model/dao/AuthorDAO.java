package edu.developodo.model.dao;

import edu.developodo.model.connection.ConnectionMariaDB;
import edu.developodo.model.entity.Author;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements DAO<Author,String> {
    private final static String INSERT="INSERT INTO author (dni,name) VALUES (?,?)";
    private final static String UPDATE="UPDATE author SET name=? WHERE dni=?";
    private final static String FINDALL="SELECT a.dni,a.name FROM author AS a";
    private final static String FINDBYDNI="SELECT a.dni,a.name FROM author AS a WHERE a.dni=?";
    private final static String DELETE="DELETE FROM author AS a WHERE a.dni=?";

    @Override
    public Author save(Author entity) {
        Author result = entity;
        if(entity==null || entity.getDni()==null) return result;
        Author a = findById(entity.getDni());
        if(a==null){
            //INSERT
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1,entity.getDni());
                        pst.setString(2,entity.getName());
                        pst.executeUpdate();
                        //si fuera autoincremental yo tendría que leer getGeneratedKeys() -> setDNI
                        /*  ResultSet res = pst.getGeneratedKeys();
                            if(rs.next()){
                                entity.setDni(rs.getStrng(1));
                             }
                         */

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            //UPDATE
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1,entity.getName());
                pst.setString(2,entity.getDni());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Author delete(Author entity) {
        if(entity==null || entity.getDni()==null) return entity;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1,entity.getDni());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Author findById(String key) {
        Author result = new Author();
        if(key==null) return result;

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYDNI)) {
            pst.setString(1,key);
            ResultSet res = pst.executeQuery();
            if(res.next()){
                result.setDni(res.getString("dni"));
                result.setName(res.getString("name"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Author> findAll() {
        List<Author> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                Author a = new Author();
                a.setDni(res.getString("dni"));
                a.setName(res.getString("name"));
                result.add(a);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }
}
