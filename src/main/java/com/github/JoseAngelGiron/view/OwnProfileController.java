package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.view.modifiedClasses.SongCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.UserDAO.*;
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
    private FlowPane users;

    @FXML
    private FlowPane songFlowPane;


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
        showLatestSongs();
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

    private void showFollowers() {

        List<User> followers = findFollowers(currentUser.getId());


        users.getChildren().clear();

        for (User user : followers) {

            VBox userBox = new VBox(10);
            userBox.setAlignment(Pos.CENTER);


            ImageView imageView = new ImageView();
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);

            byte[] photo = user.getPhoto();
            if (photo != null) {
                imageView.setImage(bytesToImage(photo));
            } else {
                File defaultFile = new File(DEFAULTIMAGE);
                if (defaultFile.exists()) {
                    imageView.setImage(new Image(defaultFile.toURI().toString()));
                }
            }

            Label nameLabel = new Label(user.getName());
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

            userBox.getChildren().addAll(imageView, nameLabel);

            users.getChildren().add(userBox);
        }
    }

    private void showLatestSongs() {
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        List<Song> songList = findLastSongsSearched(currentUser.getId());


        ObservableList<Song> observableSongs = FXCollections.observableArrayList(songList);


        songFlowPane.getChildren().clear();


        for (Song song : observableSongs) {
            SongCell songCell = new SongCell();
            songCell.updateItem(song, false);

            songFlowPane.getChildren().add(songCell.getGraphic());
        }
    }


}
