package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.ArtistDAO;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.ArtistDAO.findArtistById;
import static com.github.JoseAngelGiron.model.dao.UserDAO.deleteSearchs;

public class ConfigurationController extends Controller implements Initializable {

    @FXML
    private Label requestLabel;

    @FXML
    private Label resultRequestLabel;

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
    @FXML
    private void registerAsArtist(){
        boolean found = true;
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        Artist artistToInsert = new Artist(currentUser.getId(), "ninguno", false);

        ArtistDAO artistDAO = new ArtistDAO(artistToInsert);

        Artist artistTofind = artistDAO.findById(currentUser.getId());

        if (artistTofind.getId() > 0) {

            resultRequestLabel.setText("Ya está registrado como artista");
            resultRequestLabel.setVisible(true);
            found = false;
        }

        if (found) {
            artistDAO.insert();
            Artist artistLogged = findArtistById(currentUser.getId());

            UserSession.UserSession().setUserIntoSession(artistLogged);

            resultRequestLabel.setText("Registro exitoso");
            resultRequestLabel.setVisible(true);

        }


    }

    @FXML
    private void deleteRecords(){
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        deleteSearchs(currentUser.getId());
        resultRequestLabel.setVisible(true);
        resultRequestLabel.setText("Historial borrado");
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
