package edu.developodo.test;

import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.entity.Author;

import java.util.List;

public class test2Insert {
    public static void main(String[] args) {
        AuthorDAO aDAO = new AuthorDAO();
        List<Author> as = aDAO.findAll();
        System.out.println(as);
    }
}
