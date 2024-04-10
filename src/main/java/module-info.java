module edu.developodo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens edu.developodo to javafx.fxml;
    opens edu.developodo.model.connection to java.xml.bind;

    exports edu.developodo;
}
