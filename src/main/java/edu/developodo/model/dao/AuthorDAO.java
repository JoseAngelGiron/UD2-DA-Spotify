package edu.developodo.model.dao;

import edu.developodo.model.connection.ConnectionMariaDB;
import edu.developodo.model.entity.Author;
import edu.developodo.model.entity.Book;

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
        Author a = findById(entity.getDni());  //si no está devuelve un autor por defecto
        if(a.getDni()==null){
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
                        if(entity.getBooks()!=null) {
                            for (Book b : entity.getBooks()) {
                                BookDAO.build().save(b);
                            }
                        }

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
    public Author delete(Author entity) throws SQLException {
        if(entity==null || entity.getDni()==null) return entity;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1,entity.getDni());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Author findById(String key) {
        Author result = new AuthorLazy();
        if(key==null) return result;

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYDNI)) {
            pst.setString(1,key);
            ResultSet res = pst.executeQuery();
            if(res.next()){
                result.setDni(res.getString("dni"));
                result.setName(res.getString("name"));
                //Lazy
                //BookDAO bDAO = new BookDAO();
                //result.setBooks(bDAO.findByAuthor(result));
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
                Author a = new AuthorLazy();
                a.setDni(res.getString("dni"));
                a.setName(res.getString("name"));
                //Lazy
                // a.setBooks(BookDAO.build().findByAuthor(a));
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

    public static AuthorDAO build(){
        return new AuthorDAO();
    }
}
class AuthorLazy extends Author{
 @Override
 public List<Book> getBooks(){
        if(super.getBooks()==null){
            setBooks(BookDAO.build().findByAuthor(this));
        }
        return super.getBooks();
 }
}
