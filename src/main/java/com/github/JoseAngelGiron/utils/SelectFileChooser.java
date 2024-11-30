package com.github.JoseAngelGiron.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SelectFileChooser {

    public static File selectWAV(Stage stage) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter filtroMP3 = new FileChooser.ExtensionFilter("Archivos WAV (*.wav)", "*.wav");
        fileChooser.getExtensionFilters().add(filtroMP3);

        return fileChooser.showOpenDialog(stage);

    }

    public static File selectPhoto(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        return fileChooser.showOpenDialog(stage);

    }
}
