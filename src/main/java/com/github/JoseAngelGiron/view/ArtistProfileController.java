package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.ListSongs;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArtistProfileController extends Controller implements Initializable {

    @FXML
    private Label artistName;

    @FXML
    private ImageView artistPhoto; //¿guardar configuración donde?


    @FXML
    private TableView<Song> listOfSongsTable;

    @FXML
    private TableColumn<ImageView, Song> photoColumn;

    @FXML
    private TableColumn<String, Song> nameSongColumn;

    @FXML
    private TableColumn<Integer, Song> numberOfPlaysColumn;

    @FXML
    private TableColumn<Integer, Song> songDurationColumn;




    @FXML
    private TableView<Album> albumsTable;

    @FXML
    private TableColumn<ImageView, Album> albumPhotoColumn;

    @FXML
    private TableColumn<String, Album> nameAlbum;


    @FXML
    private TableView<User> artistFollowersTable; //otros artistas que sigue el artista en cuestión

    @FXML
    private TableColumn<ImageView, User> photoFollowerColumn;

    @FXML
    private TableColumn<String, User> nameFollowerColumn;


    //Debajo de cada tabla me monto las columnas en cuestión

    private ObservableList<Song> songsOfArtist; //las mas populares
    private ObservableList<Album> AlbumsOfArtist;
    private ObservableList<User> usersFollowers;





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
