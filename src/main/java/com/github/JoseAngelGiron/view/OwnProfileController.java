package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    private User currentUser;

    //Tablas y consultas para lo que me falta


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


}
