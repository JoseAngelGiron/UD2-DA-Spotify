package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/main"),

    LOGIN("view/login"),
    REGISTER("view/register"),

    HOME("view/home"),

    OWNPROFILE("view/ownProfile"),
    ARTISTOPTIONS("view/artistOptions"),
    MODIFYALBUM("view/modifyAlbum"),
    SEARCH("view/search"),

    ARTISTPROFILE("view/artistProfile"),
    USERPROFILE("view/userProfile"),

    CONFIGURATION("view/configuration"),
    ADMIN("view/admin");


    private String url;

    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
