package com.github.JoseAngelGiron.model.entity;

import java.util.List;
import java.util.Set;

import static com.github.JoseAngelGiron.utils.security.Security.encryptPassword;

public class User extends SpotifyElement{

    protected String password;
    protected String photo;

    protected String userName;
    protected String surname;
    protected String email;
    protected String dni;
    protected String address;
    protected Set<User> userFollowed;
    protected List<Album> albums;
    protected List<ListSongs> listSongsList;

    public User() {
    }

    public User(int id, String nick, String password, String photo,String userName, String surname, String email, String dni, String address) {
        super(id, nick);
        setPassword(password);
        this.photo = photo;
        this.userName = userName;
        this.surname = surname;
        this.email = email;
        this.dni = dni;
        this.address = address;
    }

    public User(String name, String password, String email) {
        super(name);
        setPassword(password);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() != 64) {
            this.password = encryptPassword(password);
        } else {
            this.password = password;
        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<User> getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(Set<User> userFollowed) {
        this.userFollowed = userFollowed;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<ListSongs> getListSongsList() {
        return listSongsList;
    }

    public void setListSongsList(List<ListSongs> listSongsList) {
        this.listSongsList = listSongsList;
    }
}
