package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/main"),
    LOGIN("view/login"),
    REGISTER("view/register");


    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
