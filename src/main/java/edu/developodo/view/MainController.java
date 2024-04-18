package edu.developodo.view;

import edu.developodo.App;
import edu.developodo.model.dao.AuthorDAO;
import edu.developodo.model.entity.Author;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
   @FXML
   private TableView<Author> tableView;

   @FXML
   private TableColumn<Author,String> columnDNI;
   @FXML
   private TableColumn<Author,String> columnName;

   private ObservableList<Author> authors;

    @Override
    public void onOpen(Object input) {
            //Al inicio
        List<Author> authors = AuthorDAO.build().findAll();
        System.out.println(authors);
        this.authors = FXCollections.observableArrayList(authors);
        tableView.setItems(this.authors);
    }

    @Override
    public void onClose(Object output) {

    }

    public void saveAuthor(Author newAuthor){
        AuthorDAO.build().save(newAuthor);
        this.authors.add(newAuthor);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        columnDNI.setCellValueFactory(author-> new SimpleStringProperty(author.getValue().getDni()));
        columnName.setCellValueFactory(author-> new SimpleStringProperty(author.getValue().getName()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            if(event.getNewValue()== event.getOldValue()){
                return;
            }

            if(event.getNewValue().length()<=60){
                Author author = event.getRowValue();
                author.setName(event.getNewValue());
                AuthorDAO.build().save(author);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasao!!!!!");
                alert.show();
            }
            //Actualizar los datos



        });

    }

    @FXML
    private void agregaAuthor() throws IOException {
        App.currentController.openModal(Scenes.FORMAUTHOR,"Agregando un autor...",this,null);
    }
}
