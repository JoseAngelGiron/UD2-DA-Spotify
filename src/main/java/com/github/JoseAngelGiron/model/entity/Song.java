package com.github.JoseAngelGiron.model.entity;


public class Song extends SpotifyElement{
    protected String lyrics;
    protected byte[] songFile;
    protected String musicalGender;
    protected Album album;

    public Song() {

    }

    public Song(int id, String name, byte[] songFile, String musicalGender) {
        super(id, name);
        this.songFile = songFile;
        this.musicalGender = musicalGender;
    }

    public Song( String name, byte[] songFile, String musicalGender, Album album) {
        super(name);
        this.songFile = songFile;
        this.musicalGender = musicalGender;
        this.album = album;
    }


    public byte[] getSongFile() {
        return songFile;
    }

    public void setSongFile(byte[] songFile) {
        this.songFile = songFile;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getMusicalGender() {
        return musicalGender;
    }

    public void setMusicalGender(String musicalGender) {
        this.musicalGender = musicalGender;
    }



    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
