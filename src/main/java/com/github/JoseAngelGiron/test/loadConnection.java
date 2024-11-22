package com.github.JoseAngelGiron.test;


import com.github.JoseAngelGiron.model.connection.ConnectionProperties;
import com.github.JoseAngelGiron.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
