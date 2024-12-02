package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.AlbumDAO;
import com.github.JoseAngelGiron.model.entity.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.ArtistDAO.findAmountOfPlays;
import static com.github.JoseAngelGiron.model.dao.SongDAO.findMostPopularSongs;
import static com.github.JoseAngelGiron.model.dao.UserDAO.insertIntoFriends;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;

public class ArtistProfileController extends Controller implements Initializable {

    private final static String ICONVERIFIED ="C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\6928991.png";
    private final static String ICONNOTVERIFIED ="C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\not.png";


    @FXML
    private Label artistName;

    @FXML
    private ImageView artistPhoto;

    @FXML
    private Label totalPlays;

    @FXML
    private Label resultFollow;

    @FXML
    private ImageView verified;


    @FXML
    private TableView<Song> listOfSongsTable;

    @FXML
    private TableColumn<Song, ImageView> photoColumn;

    @FXML
    private TableColumn<Song, String> nameSongColumn;

    @FXML
    private TableColumn<Song, Integer> numberOfPlaysColumn;




    @FXML
    private TableView<Album> albumsTable;

    @FXML
    private TableColumn<Album, ImageView> albumPhotoColumn;

    @FXML
    private TableColumn<Album, String> nameAlbum;


    @FXML
    private TableView<User> artistFollowersTable;
    @FXML
    private TableColumn<ImageView, User> photoFollowerColumn;

    @FXML
    private TableColumn<String, User> nameFollowerColumn;

    private Artist artistSelected;
    private ObservableList<Song> songsOfArtist; //las mas populares
    private ObservableList<Album> AlbumsOfArtist;
    private ObservableList<User> usersFollowers;





    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        artistSelected = (Artist) input;
        showDataArtist();
        showMostPopularSongs();
        showAlbums();
        resultFollow.setVisible(false);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void followArtist(){
        UserSession user = UserSession.UserSession();
        boolean follow = insertIntoFriends(user.getUserLoggedIn().getId(), artistSelected.getId());

        if (follow){
            resultFollow.setVisible(true);
            resultFollow.setStyle("-fx-text-fill: green;");
            resultFollow.setText("Ahora sigues a " + artistSelected.getName());
        }else{
            resultFollow.setVisible(true);
            resultFollow.setStyle("-fx-text-fill: red;");
            resultFollow.setText("Ya sigues a este artista");
        }



    }

    private void showDataArtist(){
        artistName.setText(artistSelected.getName());
        artistPhoto.setImage(bytesToImage(artistSelected.getPhoto()));
        int totalPlays = findAmountOfPlays(artistSelected.getId());
        this.totalPlays.setText(this.totalPlays.getText()+" "+totalPlays);

        if(artistSelected.isVerified()){
            File file = new File(ICONVERIFIED);
            if(file.exists()){
                Image defaultImage = new Image(file.toURI().toString());
                verified.setImage(defaultImage);
            }

        }else{
            File file = new File(ICONNOTVERIFIED);
            if(file.exists()){
                Image defaultImage = new Image(file.toURI().toString());
                verified.setImage(defaultImage);
            }
        }

    }

    private void showMostPopularSongs(){
        List<Song> songList = findMostPopularSongs(artistSelected.getId());
        songsOfArtist = FXCollections.observableArrayList(songList);

        listOfSongsTable.setItems(songsOfArtist);

        photoColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView();
            imageView.setImage(bytesToImage(cellData.getValue().getPhotoSong()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);

        });

        nameSongColumn.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            String name = song.getName();
            return new SimpleStringProperty(name);
        });

        numberOfPlaysColumn.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            int numberOfPlays = song.getNumberOfPlays();
            return new ReadOnlyObjectWrapper<>(numberOfPlays);
        });

    }

    private void showAlbums() {

        AlbumDAO albumDAO = new AlbumDAO();
        List<Album> albumList = albumDAO.findListOfAlbumByArtistID(artistSelected.getId());

        AlbumsOfArtist = FXCollections.observableArrayList(albumList);

        albumsTable.setItems(AlbumsOfArtist);

        albumPhotoColumn.setCellValueFactory(cellData -> {
            Album album = cellData.getValue();
            ImageView imageView = new ImageView(bytesToImage(album.getImage()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        nameAlbum.setCellValueFactory(cellData -> {
            Album album = cellData.getValue();
            return new SimpleStringProperty(album.getName());
        });
    }

}
