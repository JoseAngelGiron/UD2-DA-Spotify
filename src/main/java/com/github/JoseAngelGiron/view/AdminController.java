package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.dao.AdminDAO;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.ArtistDAO.build;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;

public class AdminController extends Controller implements Initializable {

    private final static String DEFAULTIMAGE = "C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\interrogante.jpg";

    @FXML
    private TextField searchNameBar;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, ImageView> photoUserColumn;

    @FXML
    private TableColumn<User, String> nameUserColumn;

    @FXML
    private TableColumn<User, String> emailUserColumn;




    @FXML
    private ImageView userImage;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Pane userPane;

    @FXML
    private Label result;


    private User userSelected;
    private UserDAO userDAO;
    private AdminDAO adminDAO;




    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void searchUsers(){
        String search = searchNameBar.getText();

        if(!search.isEmpty()){
            userDAO = new UserDAO();
            List<User> users = userDAO.findByName(search);


            ObservableList<User> userList = FXCollections.observableArrayList(users);
            userTableView.setItems(userList);

            photoUserColumn.setCellValueFactory(cellData -> {
                ImageView imageView = new ImageView(bytesToImage(cellData.getValue().getPhoto()));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new SimpleObjectProperty<>(imageView);
            });

            nameUserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            emailUserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));


            userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    userSelected = newValue;
                    updateUserDetails(userSelected);
                    userPane.setVisible(true);
                }
            });


        }

    }

    @FXML
    private void deleteUser() throws SQLException, InterruptedException {

        userDAO = new UserDAO(userSelected);
        userDAO.delete();

        result.setText("Usuario borrado");
        result.setVisible(true);
        searchUsers();
        Thread.sleep(2000);
        userPane.setVisible(false);
    }

    @FXML
    private void makeAdmin() throws InterruptedException {
        adminDAO = new AdminDAO(userSelected);
        Artist artist = build().findById(userSelected.getId());
        Admin admin = adminDAO.findById(userSelected.getId());

        if(artist.getId() == 0 && admin.getId() == 0){

            adminDAO.insert();

            result.setText("Usuario insertado con éxito como administrador");
            result.setVisible(true);
            searchUsers();
            Thread.sleep(2000);
            userPane.setVisible(false);

        }else{
            result.setText("El usuario ya se encuentra registrado como artista o ya es admin");
            result.setVisible(true);
        }



    }

    private void updateUserDetails(User user) {
        userNameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());


        if (user.getPhoto() != null) {
            userImage.setImage((bytesToImage(user.getPhoto())));
        } else {
            File file = new File(DEFAULTIMAGE);
            if(file.exists()){
                Image defaultImage = new Image(file.toURI().toString());
                userImage.setImage(defaultImage);
            }
        }
    }
}
