package com.github.JoseAngelGiron.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.github.JoseAngelGiron.App;


import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static com.github.JoseAngelGiron.view.AppController.changeScene;


public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField emailText;
    @FXML
    private TextField passwordText;

    private User userToLogin;

    @FXML
    private Label loginError;


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
    private void LogUser() throws IOException {
        userToLogin = new User();
        String email = emailText.getText();
        String password = passwordText.getText();

        UserDAO userDAO = new UserDAO();

        userToLogin = userDAO.findByEmailAndPassword(email, password);

        if(userToLogin.getId()>-1){

            UserSession session = UserSession.UserSession();
            session.setUserIntoSession(userToLogin);
            changeToMainWindow();

        }

    }

    /**
     * Changes the current scene to the registration scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToRegister() throws IOException {
        App.setRoot(Scenes.REGISTER.getURL());

        App.scene.getWindow().setWidth(420);
        App.scene.getWindow().setHeight(720);

    }

    /**
     * Changes the current scene to the mainWindow scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToMainWindow() throws IOException {

        App.scene.getWindow().setWidth(1100);
        App.scene.getWindow().setHeight(720);
        App.scene.getWindow().centerOnScreen();

        App.setRoot(Scenes.ROOT.getURL());

    }

    /**
     * Resizes the application window to fill the entire screen.
     * It sets the stage position to the top-left corner and adjusts the width and height to match the screen size.
     * The window becomes resizable after resizing.
     */
    public static void resizeWindow() {

        Stage stage = (Stage) App.scene.getWindow();

        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        stage.setX(screenSize.getMinX());
        stage.setY(screenSize.getMinY());
        stage.setWidth(screenSize.getWidth());
        stage.setHeight(screenSize.getHeight());

        stage.setResizable(true);
    }
}
