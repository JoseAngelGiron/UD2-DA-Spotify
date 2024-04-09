module edu.developodo {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.developodo to javafx.fxml;
    exports edu.developodo;
}
