package com.github.JoseAngelGiron.model.entity;


public class Song extends SpotifyElement{

    protected byte[] songFile;

    protected String musicalGender;
    protected Artist artistOwner;
    protected Album album;

    public Song() {

    }

    public Song(int id, String name, byte[] songFile, String musicalGender) {
        super(id, name);
        this.songFile = songFile;
        this.musicalGender = musicalGender;
    }

    public byte[] getSongFile() {
        return songFile;
    }

    public void setSongFile(byte[] songFile) {
        this.songFile = songFile;
    }



    public String getMusicalGender() {
        return musicalGender;
    }

    public void setMusicalGender(String musicalGender) {
        this.musicalGender = musicalGender;
    }

    public Artist getArtistOwner() {
        return artistOwner;
    }

    public void setArtistOwner(Artist artistOwner) {
        this.artistOwner = artistOwner;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
