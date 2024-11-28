package com.github.JoseAngelGiron;


import com.github.JoseAngelGiron.view.AppController;
import com.github.JoseAngelGiron.view.Scenes;
import com.github.JoseAngelGiron.view.View;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;



    /**
     * The main entry point for the JavaFX application.
     * This method is called when the application is launched.
     * It sets the initial scene, configures the stage, and displays it.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Scenes.LOGIN.getURL()), 600, 430);
        stage.setScene(scene);
        stage.setResizable(false);


        stage.setTitle("Login");
        stage.show();

    }

    /**
     * Changes the root of the current scene to the specified FXML file.
     *
     * @param fxml The name of the FXML file to load (without the .fxml extension).
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    /**
     * Loads the specified FXML file and returns the corresponding Parent node.
     *
     * @param fxml The name of the FXML file to load (without the .fxml extension).
     * @return The Parent node loaded from the FXML file.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The main method for launching the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

}