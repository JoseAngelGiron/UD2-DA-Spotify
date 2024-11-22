package com.github.JoseAngelGiron.model.entity;


public class Song extends SpotifyElement{

    protected String urlFile;
    protected String urlImage;
    protected String musicalGender;
    protected Artist artistOwner;
    protected Album album;

    public Song() {

    }

    public Song(int id, String name, String urlFile, String urlImage, String musicalGender) {
        super(id, name);
        this.urlFile = urlFile;
        this.urlImage = urlImage;
        this.musicalGender = musicalGender;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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
