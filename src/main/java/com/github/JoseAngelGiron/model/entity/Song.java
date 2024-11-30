package com.github.JoseAngelGiron.model.entity;


public class Song extends SpotifyElement{

    protected byte[] songFile;
    protected byte[] photoSong;
    protected String musicalGender;
    protected Album album;

    public Song() {

    }

    public Song(int id, String name, byte[] songFile, String musicalGender) {
        super(id, name);
        this.songFile = songFile;
        this.musicalGender = musicalGender;
    }

    public Song( String name, byte[] songFile, byte[] photoSong, String musicalGender, Album album) {
        super(name);
        this.songFile = songFile;
        this.photoSong = photoSong;
        this.musicalGender = musicalGender;
        this.album = album;
    }


    public byte[] getSongFile() {
        return songFile;
    }

    public void setSongFile(byte[] songFile) {
        this.songFile = songFile;
    }

    public byte[] getPhotoSong() {
        return photoSong;
    }

    public void setPhotoSong(byte[] photoSong) {
        this.photoSong = photoSong;
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
