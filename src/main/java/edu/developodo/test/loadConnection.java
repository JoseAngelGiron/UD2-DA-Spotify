package edu.developodo.test;

import edu.developodo.model.connection.ConnectionProperties;
import edu.developodo.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
