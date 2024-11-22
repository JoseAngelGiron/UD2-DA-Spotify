package com.github.JoseAngelGiron.model.entity;

public class Admin extends User{

    protected boolean isAdmin;

    public Admin() {
    }

    public Admin(int id, String nick, String password, String photo,String userName, String surname, String email, String dni, String address, boolean isAdmin) {
        super(id, nick, password, photo, userName, surname, email, dni, address);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
