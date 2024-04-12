package edu.developodo.model.dao;

import edu.developodo.model.connection.ConnectionMariaDB;
import edu.developodo.model.entity.Author;
import edu.developodo.model.entity.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements DAO<Book,String> {
    private static final String FINDALL ="SELECT b.isbn,b.title,b.id_author FROM book AS b";
    private static final String FINDBYID ="SELECT b.isbn,b.title,b.id_author FROM book AS b WHERE b.isbn=?";
    private static final String INSERT ="INSERT INTO book (isbn,title,id_author) VALUES (?,?,?)";
    private static final String UPDATE ="UPDATE book SET title=? WHERE isbn=?";
    private static final String DELETE ="DELETE FROM book WHERE isbn=?";
    private static final String FINDBYAUTHOR ="SELECT b.isbn,b.title,b.id_author FROM book AS b WHERE b.id_author=?";


    private Connection conn;
    public BookDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Book save(Book entity) {
        Book result=entity;
        if(entity!=null){
            String isbn = entity.getIsbn();
            if(isbn!=null){
                Book isInDataBase = findById(isbn);
                if(isInDataBase==null){
                    //INSERT
                    try(PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1,entity.getIsbn());
                        pst.setString(2,entity.getTitle());
                        pst.setString(3,entity.getAuthor().getDni());
                        pst.executeUpdate();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    //UPDATE
                    try(PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1,entity.getTitle());
                        pst.setString(2,entity.getIsbn());
                        pst.executeUpdate();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Book delete(Book entity) {
        if(entity!=null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getIsbn());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

    @Override
    public Book findById(String key) {
        Book result = null;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYID)){
            pst.setString(1,key);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    Book b = new Book();
                    b.setIsbn(res.getString("isbn"));
                    //Eager
                    b.setAuthor(AuthorDAO.build().findById(res.getString("id_author")));
                    b.setTitle(res.getString("title"));
                    result=b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
       List<Book> result = new ArrayList<>();
        try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Book b = new Book();
                    b.setIsbn(res.getString("isbn"));
                   //Eager
                    // b.setAuthor(res.getString("author"));
                    b.setTitle(res.getString("title"));
                    result.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    public List<Book> findByAuthor(Author a) {
        List<Book> result = new ArrayList<>();
        if(a==null || a.getDni()==null) return result;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYAUTHOR)){
            pst.setString(1,a.getDni());
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Book b = new Book();
                    b.setIsbn(res.getString("isbn"));
                    b.setAuthor(a);
                    b.setTitle(res.getString("title"));
                    result.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static BookDAO build(){
        return new BookDAO();
    }
}
