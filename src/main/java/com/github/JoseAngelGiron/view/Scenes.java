package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/layout"),
    LOGIN("view/login");


    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
