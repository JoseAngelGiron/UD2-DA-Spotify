package edu.developodo.test;

import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.entity.Author;
import edu.developodo.model.entity.Book;

public class testCascade {
    public static void main(String[] args) {
        Author a = AuthorDAO.build().findById("1");
        a.getBooks();
        System.out.println(a);

        a.removeBook(new Book("2",null,null));
        a.addBook(new Book("3","La Biografía de Cervantes",a));

        Book book = a.getBook(new Book("1",null,null));
        book.setTitle("Don Quijote de la Mancha");

        AuthorDAO.build().save(a);
    }
}
