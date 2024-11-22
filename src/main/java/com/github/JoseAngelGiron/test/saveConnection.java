package com.github.JoseAngelGiron.test;


import com.github.JoseAngelGiron.model.connection.ConnectionProperties;
import com.github.JoseAngelGiron.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","library","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
