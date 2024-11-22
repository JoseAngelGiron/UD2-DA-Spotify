package com.github.JoseAngelGiron.model.entity;


import java.util.Set;

public class Album extends SpotifyElement{

    protected String image;
    protected int year;
    protected Set<User> users;

    public Album() {

    }

    public Album(int id, String name, String image, int year) {
        super(id, name);
        this.image = image;
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
