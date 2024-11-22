package com.github.JoseAngelGiron.model;


import com.github.JoseAngelGiron.model.entity.User;

public class UserSession {

    private static UserSession _instance;
    private User userLoggedIn;

    private UserSession(){
        userLoggedIn = new User();
    }

    public static UserSession UserSession(){
        if(_instance==null){
           _instance= new UserSession();
        }
        return _instance;

    }

    /**
     * Closes the current user session.
     * This method sets the logged-in user to null, effectively ending the user's session.
     * After this method is called, the user will be logged out, and any session-related data
     * associated with the user will no longer be accessible.
     * @return void
     */
    public void closeSession(){
        userLoggedIn = null;
    }

    public void setUserIntoSession(User user){
        this.userLoggedIn = user;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

}
