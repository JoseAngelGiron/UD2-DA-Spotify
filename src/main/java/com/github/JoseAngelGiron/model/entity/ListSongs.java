package com.github.JoseAngelGiron.model.entity;

import java.util.List;
import java.util.Set;

public class ListSongs extends SpotifyElement{
    protected String photo;
    protected List<Song> listOfSongs;
    protected Set<User> users;

    public ListSongs() {

    }

    public ListSongs(int id, String name, String photo) {
        super(id, name);
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
