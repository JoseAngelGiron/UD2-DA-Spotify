package com.github.JoseAngelGiron.model.entity;

public class Artist extends User{

    protected String musicalGender;
    protected boolean verified;
    private int totalPlays;

    public Artist() {
    }

    public Artist(int id, String nick, String password, byte[] photo, String userName, String surname, String email, String dni, String address, String musicalGender, boolean verified) {
        super(id, nick, password, photo, userName, surname, email, dni, address);
        this.musicalGender = musicalGender;
        this.verified = verified;
    }

    public Artist(User user, String musicalGender, boolean verified) {
        super(user.getId(), user.getName(), user.getPassword(), user.getPhoto(), user.getUserName(),
                user.getSurname(), user.getEmail(), user.getDni(), user.getAddress());
        this.musicalGender = musicalGender;
        this.verified = verified;
    }



    public Artist(int id, String musicalGender, boolean verified) {
        super(id);
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

    public int getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(int totalPlays) {
        this.totalPlays = totalPlays;
    }
}
