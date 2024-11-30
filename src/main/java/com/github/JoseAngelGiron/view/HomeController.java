package com.github.JoseAngelGiron.view;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.SongPlayer.stopSong;


public class HomeController extends Controller implements Initializable {

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stopSong();

    }
}
