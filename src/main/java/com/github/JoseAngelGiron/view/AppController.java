package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.App;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.SongDAO.findSongAlbumAndArtist;


public class AppController extends Controller implements Initializable {
    @FXML
    private Pane mainWindow;

    @FXML
    private ScrollPane scrollMainPane;

    @FXML
    private Button administrationButton;

    @FXML
    private TextField searchField;

    @FXML
    private MenuItem profile;

    @FXML
    private MenuItem configuration;

    @FXML
    private MenuItem artistOptions;

    @FXML
    private MenuItem admin;

    @FXML
    private MenuItem closeSession;

    @FXML
    private Label noResultsFound;




    public static Controller centerController;
    public static Controller modalController;


    @Override
    public void onOpen(Object input, Object data) throws IOException {

    }


    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAdministrationButton();
        showArtistButton();

        try {
            changeToHome();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }

    }

    /**
     * Opens a modal dialog window.
     * @param scene The scene to be displayed in the modal window.
     * @param title The title of the modal window.
     * @param parent The parent controller of the modal window.
     * @param data Additional data to be passed to the modal window.
     * @throws IOException If an error occurs while loading the FXML for the modal window.
     */
    public static void openModal(Scenes scene, String title, Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent, data);
        stage.showAndWait();
        modalController=view.controller;
    }

    /**
     * Changes the displayed scene in a Pane container with a new scene by loading an FXML file
     * and associating it with its controller.
     *
     * @param scene The scene to be loaded.
     * @param pane  The Pane container in which the new scene will be shown.
     * @param data  Optional data to be passed to the controller of the new scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static void changeScene(Scenes scene, Pane pane, Object data) throws IOException {
        pane.getChildren().clear();
        View view = loadFXML(scene);
        pane.getChildren().add(view.scene);
        centerController = view.controller;

        centerController.onOpen(data, null);
    }

    public static void changeScene(Scenes scene, ScrollPane scrollPane, Object data) throws IOException {

        scrollPane.setContent(null);

        View view = loadFXML(scene);

        scrollPane.setContent(view.scene);

        centerController = view.controller;
        centerController.onOpen(data, null);
    }


    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url + ".fxml"));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }


    @FXML
    public void changeToSearch() throws IOException {
        String search = searchField.getText();
        changeScene(Scenes.SEARCH, scrollMainPane, search);
    }


    /**
     * Changes the scene to the administration area.
     * @throws IOException If an error occurs while loading the administration view.
     */
    @FXML
    public void changeToLogin() throws IOException {
        changeScene(Scenes.LOGIN, mainWindow, null);
        App.scene.getWindow().setWidth(720);
        App.scene.getWindow().setHeight(580);

        App.scene.getWindow().centerOnScreen();
    }

    /**
     * Changes the scene to the profile area.
     * @throws IOException If an error occurs while loading the profile view.
     */
    @FXML
    public void changeToProfile() throws IOException {
        changeScene(Scenes.OWNPROFILE, scrollMainPane, null);
    }

    /**
     * Changes the scene to the configuration area.
     * @throws IOException If an error occurs while loading the profile view.
     */
    @FXML
    public void changeToConfiguration() throws IOException {
        changeScene(Scenes.CONFIGURATION, scrollMainPane, null);
    }

    /**
     * Changes the scene to the admin area.
     * @throws IOException If an error occurs while loading the admin view.
     */
    @FXML
    public void changeToAdminArea() throws IOException {
        changeScene(Scenes.ADMIN, scrollMainPane, null);
    }

    /**
     * Changes the scene to the artistOptions area.
     * @throws IOException If an error occurs while loading the admin view.
     */
    @FXML
    public void changeToArtistOptions() throws IOException {
        changeScene(Scenes.ARTISTOPTIONS, scrollMainPane, null);
    }



    /**
     * Changes the scene to the home area.
     * @throws IOException If an error occurs while loading the home view.
     */
    @FXML
    public void changeToHome() throws IOException {
        changeScene(Scenes.HOME, scrollMainPane, null);
    }


    /**
     * Show the administration button based on the user's role.
     */
    public void showAdministrationButton() {
        User currentuser = UserSession.UserSession().getUserLoggedIn();

        if(currentuser.getClass().equals(Admin.class)){
           Admin userAdmin = (Admin) currentuser;
           if(userAdmin.isAdmin()){
               admin.setVisible(true);
           }
        }
    }

    /**
     * Show the artist button based on the user's role.
     */
    public void showArtistButton() {
        User currentuser = UserSession.UserSession().getUserLoggedIn();

        if(currentuser.getClass().equals(Artist.class)){
            artistOptions.setVisible(true);
        }
    }



    @FXML
    private void closeApp(){
        System.exit(0);
    }


}
