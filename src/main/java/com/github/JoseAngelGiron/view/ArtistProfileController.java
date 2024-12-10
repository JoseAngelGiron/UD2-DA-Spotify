package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.AlbumDAO;
import com.github.JoseAngelGiron.model.dao.UserDAO;
import com.github.JoseAngelGiron.model.entity.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.ArtistDAO.findAmountOfPlays;
import static com.github.JoseAngelGiron.model.dao.SongDAO.findMostPopularSongs;
import static com.github.JoseAngelGiron.model.dao.UserDAO.findIfAlreadyFriends;
import static com.github.JoseAngelGiron.model.dao.UserDAO.insertIntoFriends;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.view.AppController.changeScene;

public class ArtistProfileController extends Controller implements Initializable {

    private final static String ICONVERIFIED ="C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\6928991.png";
    private final static String ICONNOTVERIFIED ="C:\\Users\\the_l\\IdeaProjects\\Proyect-UD2-DA\\src\\main\\resources\\assets\\not.png";

    @FXML
    private AnchorPane anchorPane;

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
    private FlowPane albumsListPane;



    @FXML
    private Button deleteButton;


    private User currentUser;
    private Artist artistSelected;
    private ObservableList<Song> songsOfArtist;
    private ObservableList<Album> AlbumsOfArtist;





    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        artistSelected = (Artist) input;
        showDataArtist();
        showMostPopularSongs();
        showAlbums();
        resultFollow.setVisible(false);
        currentUser = UserSession.UserSession().getUserLoggedIn();
        boolean follow = findIfAlreadyFriends(currentUser.getId(), artistSelected.getId());

        if(follow){
            deleteButton.setVisible(true);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void followArtist(){
        boolean follow = insertIntoFriends(currentUser.getId(), artistSelected.getId());

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

    @FXML
    public void deleteFollow() throws SQLException {
        UserDAO.deleteFollow(currentUser.getId(), artistSelected.getId());
        resultFollow.setText("Ya no sigues a este usuario");
        resultFollow.setStyle("-fx-text-fill: green;");
        resultFollow.setVisible(true);

        deleteButton.setVisible(false);
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


        albumsListPane.getChildren().clear();

        for (Album album : AlbumsOfArtist) {
            VBox albumItem = new VBox();
            albumItem.setSpacing(5);
            albumItem.setStyle("-fx-alignment: center; -fx-padding: 10px;");


            ImageView albumImage = new ImageView();
            albumImage.setImage(bytesToImage(album.getImage()));
            albumImage.setFitHeight(100);
            albumImage.setFitWidth(100);
            albumImage.setPreserveRatio(true);


            Label albumName = new Label(album.getName());
            albumName.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-alignment: center;");


            albumItem.getChildren().addAll(albumImage, albumName);


            albumItem.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Album albumSelected = album;
                    if (albumSelected != null) {
                        try {
                            changeToAlbum(albumSelected);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });


            albumsListPane.getChildren().add(albumItem);
        }


        albumsListPane.setOrientation(Orientation.HORIZONTAL);
    }


    private void changeToAlbum(Album album) throws IOException {
        changeScene(Scenes.ALBUMVIEW, anchorPane, album);
    }




}
