module com.github.JoseAngelGiron {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;

    requires uk.co.caprica.vlcj;


    opens com.github.JoseAngelGiron to javafx.fxml;
    opens com.github.JoseAngelGiron.model.connection to java.xml.bind;

    exports com.github.JoseAngelGiron;
    exports com.github.JoseAngelGiron.view;
    opens com.github.JoseAngelGiron.view to javafx.fxml;
}
