package edu.developodo.test;

import edu.developodo.model.dao.BookDAO;
import edu.developodo.model.entity.Author;
import edu.developodo.model.entity.Book;

import java.util.List;

public class testSELECT {
    public static void main(String[] args) {
        BookDAO bDAO = new BookDAO();
        Author a = new Author();
        a.setDni("1");
        List<Book> books = bDAO.findByAuthor(a);
        System.out.println(books);
    }
}
