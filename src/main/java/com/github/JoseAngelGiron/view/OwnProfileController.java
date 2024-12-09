package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.UserDAO.build;
import static com.github.JoseAngelGiron.model.dao.UserDAO.findFollowers;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.utils.ConvertBytes.fileToByte;
import static com.github.JoseAngelGiron.utils.SelectFileChooser.selectPhoto;
import static com.github.JoseAngelGiron.view.RegisterController.validatePassword;

public class OwnProfileController extends Controller implements Initializable {

    private final static String DEFAULTIMAGE = "C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\interrogante.jpg";

    @FXML
    private ImageView photoUser;

    @FXML
    private TextField nick;

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField dni;
    @FXML
    private TextField adress;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private ListView<User> users;

    @FXML
    private ListView<Song> songs;


    private User currentUser;




    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        showData();
        showFollowers();
    }

    @FXML
    public void updateUserData(){

        currentUser.setName(nick.getText());
        currentUser.setUserName(name.getText());
        currentUser.setSurname(surname.getText());
        currentUser.setDni(dni.getText());
        currentUser.setAddress(adress.getText());
        String password = passwordField.getText();

        if(validatePassword(password)){
            currentUser.setPassword(password);
            UserDAO userDAO = new UserDAO(currentUser);
            userDAO.update();

        }else{
            passwordLabel.setStyle("-fx-text-fill: red;");
            passwordLabel.setVisible(true);
        }


    }
    @FXML
    public void updateImage(){
        File file = selectPhoto(new Stage());
        byte[] imageInBytes = fileToByte(file);

        if(imageInBytes!=null){
            photoUser.setImage(bytesToImage(imageInBytes));
            currentUser.setPhoto(imageInBytes);
        }else{
            //label informativa...
        }
    }

    private void showData(){
        nick.setText(currentUser.getName());
        name.setText(currentUser.getUserName());
        surname.setText(currentUser.getSurname());
        dni.setText(currentUser.getDni());
        adress.setText(currentUser.getAddress());

        byte[] imageUserInBytes = currentUser.getPhoto();
        if(imageUserInBytes!=null){
            Image image = bytesToImage(imageUserInBytes);
            photoUser.setImage(image);
        }else{
            File file = new File(DEFAULTIMAGE);
            if(file.exists()){
                Image defaultImage = new Image(file.toURI().toString());
                photoUser.setImage(defaultImage);
            }

        }


    }

    private void showFollowers(){

        List<User> followers = findFollowers(currentUser.getId());


        users.setCellFactory(listView -> new ListCell<User>() {
            private final ImageView imageView = new ImageView();
            private final Label nameLabel = new Label();
            private final VBox container = new VBox(10, imageView, nameLabel);

            {
                imageView.setFitHeight(75);
                imageView.setFitWidth(50);
            }

            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setGraphic(null);
                    setText(null);
                } else {

                    byte[] photo = user.getPhoto();
                    if (photo != null) {
                        imageView.setImage(bytesToImage(photo));
                    } else {

                        File defaultFile = new File(DEFAULTIMAGE);
                        if (defaultFile.exists()) {
                            imageView.setImage(new Image(defaultFile.toURI().toString()));
                        }
                    }

                    nameLabel.setText(user.getName());

                    setGraphic(container);
                }
            }
        });
        users.getItems().setAll(followers);

    }

    private void showSearchs(){

    }


}
