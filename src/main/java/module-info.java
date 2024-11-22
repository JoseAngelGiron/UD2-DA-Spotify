module edu.developodo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires java.desktop;

    opens edu.developodo to javafx.fxml;
    opens edu.developodo.model.connection to java.xml.bind;

    exports com.github.JoseAngelGiron;

    exports com.github.JoseAngelGiron.view;
    opens edu.developodo.view to javafx.fxml;
}
