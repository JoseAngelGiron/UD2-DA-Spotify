package com.github.JoseAngelGiron.model.entity;


import java.util.List;
import java.util.Set;

public class Album extends SpotifyElement{

    protected byte[] image;
    protected int year;

    protected Set<User> users;
    protected Artist artist;
    protected List<Song> songsOfAlbum;

    public Album() {

    }

    public Album(int id) {
        super(id);
    }

    public Album(int id, String name, byte[] image, int year) {
        super(id, name);
        this.image = image;
        this.year = year;
    }

    public Album( String name, byte[] image, int year, Artist artist) {
        super(name);
        this.image = image;
        this.year = year;
        this.artist = artist;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Song> getSongsOfAlbum() {
        return songsOfAlbum;
    }

    public void setSongsOfAlbum(List<Song> songsOfAlbum) {
        this.songsOfAlbum = songsOfAlbum;
    }

    @Override
    public String toString() {
        return "Album{" +
                "year=" + year +
                ", artist=" + artist +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", songsOfAlbum=" + songsOfAlbum +
                '}';
    }
}
