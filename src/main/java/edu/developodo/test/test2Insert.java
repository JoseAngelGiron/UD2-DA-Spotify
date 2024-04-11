package edu.developodo.test;

import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.entity.Author;
import edu.developodo.model.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class test2Insert {
    public static void main(String[] args) {
        AuthorDAO aDAO = new AuthorDAO();
        List<Author> as = aDAO.findAll();
        System.out.println(as);

        Author a1 = aDAO.findById("1");
        System.out.println(a1);

        Author a2 = new Author("2","Neruda",new ArrayList<>());
        a2.addBook(new Book("12","Crepusculaio",a2));
        aDAO.save(a2);

        as = aDAO.findAll();
        System.out.println(as);
    }
}
