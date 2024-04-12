package edu.developodo.test;

import edu.developodo.model.dao.BookDAO;
import edu.developodo.model.entity.Book;

public class testSELECTEager {
    public static void main(String[] args) {
        Book b=BookDAO.build().findById("1");
        System.out.println(b);
    }
}
