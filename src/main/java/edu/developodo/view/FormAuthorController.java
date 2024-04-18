package edu.developodo.view;

import edu.developodo.model.entity.Author;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FormAuthorController extends Controller implements Initializable {
    @FXML
    private TextField dni;
    @FXML
    private TextField name;

    private MainController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MainController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void closeWindow(Event event){
        Author author = new Author(dni.getText(),name.getText(),new ArrayList<>());
        this.controller.saveAuthor(author);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
