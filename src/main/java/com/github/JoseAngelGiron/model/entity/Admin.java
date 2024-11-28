package com.github.JoseAngelGiron.model.entity;

public class Admin extends User{

    protected boolean isAdmin;

    public Admin() {
    }

    public Admin(int id, String nick, String password, String photo,String userName, String surname, String email, String dni, String address, boolean isAdmin) {
        super(id, nick, password, photo, userName, surname, email, dni, address);
        this.isAdmin = isAdmin;
    }

    public Admin(User user, boolean verified) {
        super(user.getId(), user.getName(), user.getPassword(), user.getPhoto(), user.getUserName(),
                user.getSurname(), user.getEmail(), user.getDni(), user.getAddress());
        this.isAdmin = verified;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
