package edu.developodo.test;

import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.dao.BookDAO;
import edu.developodo.model.entity.Author;

public class tests2SELECT {
    public static void main(String[] args) {
        AuthorDAO aDAO = new AuthorDAO();
        Author a = aDAO.findById("1");
        System.out.println(a);

        Author b=AuthorDAO.build().findById("1");


    }
}
