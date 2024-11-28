package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController extends Controller implements Initializable {

    @FXML
    private Label requestLabel;

    @FXML
    private Button registerArtistButton;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        changeData();

    }



    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void changeData() {

        User currentuser = UserSession.UserSession().getUserLoggedIn();

        if(currentuser.getClass().equals(Admin.class)){
            Admin userAdmin = (Admin) currentuser;
            if(userAdmin.isAdmin()){
                requestLabel.setText("No se puede registrar como artista en una cuenta de administrador");
                registerArtistButton.setVisible(false);
            }
        }


    }
}
