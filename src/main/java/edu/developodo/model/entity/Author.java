package edu.developodo.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private String dni;
    private String name;
    private List<Book> books;

    public Author(String dni, String name, List<Book> books) {
        this.dni = dni;
        this.name = name;
        this.books = books;
    }

    public Author() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public void addBook(Book book){
        if(books==null){
            books = new ArrayList<>();
        }
        if(!books.contains(book)){
            books.add(book);
        }
    }
    public void removeBook(Book book){
        if(books!=null){
            books.remove(book);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(dni, author.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Author{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
