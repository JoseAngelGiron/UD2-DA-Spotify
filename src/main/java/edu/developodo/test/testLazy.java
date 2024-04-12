package edu.developodo.test;

import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.entity.Author;

public class testLazy {
    public static void main(String[] args) {
        Author a = AuthorDAO.build().findById("1");
        System.out.println(a);
        System.out.println(a.getBooks());
        System.out.println(a.getBooks());
    }
}
