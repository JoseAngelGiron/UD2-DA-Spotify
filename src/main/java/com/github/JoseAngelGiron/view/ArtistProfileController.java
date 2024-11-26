package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.ListSongs;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistProfileController extends Controller implements Initializable {

    @FXML
    private Label artistName;

    @FXML
    private ImageView artistPhoto; //¿guardar configuración donde?

    @FXML
    private TableView<ListSongs> listOfSongs;

    @FXML
    private TableView<Album> albums;

    @FXML
    private TableView<User> Artistfollowers; //otros artistas que sigue el artista en cuestión

    //Debajo de cada tabla me monto las columnas en cuestión

    private List<ListSongs> songsOfArtist; //las mas populares





    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
