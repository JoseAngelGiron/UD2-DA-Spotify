package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.App;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.github.JoseAngelGiron.model.dao.UserDAO.build;


public class RegisterController extends Controller implements Initializable {

    @FXML
    private TextField nickField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label nickLabel1;
    @FXML
    private Label passwordLabel1;
    @FXML
    private Label passwordLabel2;
    @FXML
    private Label emailLabel;

    @FXML
    private Label userAlreadyRegistered;







    @Override
    public void onOpen(Object input, Object data) throws IOException {


    }

    @Override
    public void onClose(Object output) {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        App.scene.getWindow().setHeight(400);
        App.scene.getWindow().setWidth(400);

    }


    /**
     * Registers a new user with the provided information.
     * @throws IOException If an I/O error occurs while retrieving default image, encrypting password, or displaying a modal.
     */
    @FXML
    private void registerUser() throws IOException {
        String nickName = nickField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();



        User userToVerify = build().findByEmail(email);
        System.out.println(userToVerify.getId());

        if(userToVerify.getId()!= 0 ){

            userAlreadyRegistered.setVisible(true);

        } else if (!checkFields()) {


        }else{

            UserDAO userDAO = new UserDAO(nickName, password, email);
            userDAO.insert();

        }
    }

    /**
     * Returns to the login screen.
     * @throws IOException If an I/O error occurs while navigating to the login scene.
     */
    @FXML
    private void returnToLogin() throws IOException {

        App.setRoot(Scenes.LOGIN.getURL());

        App.scene.getWindow().setWidth(720);
        App.scene.getWindow().setHeight(480);
        App.scene.getWindow().centerOnScreen();
    }


    /**
     * Checks if all input fields are valid.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean checkFields(){
        boolean valid = false;

        if (validateNick(nickField.getText()) && validatePassword(passwordField.getText()) &&
                validateEmail(emailField.getText())) {

            valid = true;
        }else{

            if(!validateNick(nickField.getText())){
                nickLabel1.setVisible(true);

            }else{
                nickLabel1.setVisible(false);

            }

            if (!validatePassword(passwordField.getText())){
                passwordLabel1.setVisible(true);
                passwordLabel2.setVisible(true);
            }else{
                passwordLabel1.setVisible(false);
                passwordLabel2.setVisible(false);
            }
            emailLabel.setVisible(!validateEmail(emailField.getText()));
        }
        return valid;
    }

    /**
     * Validates a user's nickname.
     * @param nick The nickname to validate.
     * @return true if the nickname is valid, false otherwise.
     */
    private static boolean validateNick(String nick){
        Pattern pattern = Pattern.compile("^(?!.*\\s)\\w{4,12}$");
        return pattern.matcher(nick).matches();

    }

    /**
     * Validates a user's password.
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?!.*\\s).{8,}$");
        return pattern.matcher(password).matches();

    }
    /**
     * Validates an email address.
     * @param email The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    private static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^(?!.*\\s).+@.+\\.(com|es)$");
        return pattern.matcher(email).matches();
    }

}

