package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.App;

import com.github.JoseAngelGiron.model.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController extends Controller implements Initializable {
    @FXML
    private Pane mainWindow;
    @FXML
    private Button administrationButton;

    public static Controller centerController;
    public static Controller modalController;


    @Override
    public void onOpen(Object input, Object data) throws IOException {

    }


    @Override
    public void onClose(Object output) {

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
     * Changes the scene to the store view.
     * @throws IOException If an error occurs while loading the store view.
     */
    @FXML
    public void changeStore() throws IOException {
        changeScene(Scenes.STORE, mainWindow,null);
    }
    /**
     * Changes the scene to the library view.
     * @throws IOException If an error occurs while loading the library view.
     */
    @FXML
    public void changeToLibrary() throws IOException {
        changeScene(Scenes.LIBRARY, mainWindow, Scenes.LIBRARY_USER);
    }
    /**
     * Changes the scene to the personal area.
     * @throws IOException If an error occurs while loading the personal area view.
     */
    @FXML
    public void changeToPersonalArea() throws IOException {
        changeScene(Scenes.PROFILE, mainWindow,null);
    }

    /**
     * Changes the scene to the administration area.
     * @throws IOException If an error occurs while loading the administration view.
     */
    @FXML
    public void changeToAdminArea() throws IOException {
        changeScene(Scenes.ADMIN, mainWindow, null);
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideAdministrationButton();
        try {
            changeStore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Hides the administration button based on the user's role.
     */
    public void hideAdministrationButton() {
        UserSession session = UserSession.UserSession();


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
    /**
     * Displays all users in a table view.
     */
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
    private void closeApp(){
        System.exit(0);
    }


}
