package com.github.JoseAngelGiron.model.entity;

public class Artist extends User{

    protected String musicalGender;
    protected boolean verified;

    public Artist() {
    }

    public Artist(int id, String nick, String password, String photo, String userName, String surname, String email, String dni, String address, String musicalGender, boolean verified) {
        super(id, nick, password, photo, userName, surname, email, dni, address);
        this.musicalGender = musicalGender;
        this.verified = verified;
    }

    public String getMusicalGender() {
        return musicalGender;
    }

    public void setMusicalGender(String musicalGender) {
        this.musicalGender = musicalGender;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
