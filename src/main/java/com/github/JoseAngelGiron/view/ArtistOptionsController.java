package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.AlbumDAO;
import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.sound.sampled.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;


import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.utils.ConvertBytes.imageToBytes;
import static com.github.JoseAngelGiron.view.AppController.changeScene;


public class ArtistOptionsController extends Controller implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView imageViewAlbum;

    @FXML
    private TextField albumNameTextField;

    @FXML
    private TextField yearOfReleaseTextField;


    @FXML
    private TableView<Album> albumTableView;

    @FXML
    private TableColumn<Album, ImageView> columnAlbumPhoto;

    @FXML
    private TableColumn<Album, String> columnAlbumName;


    @FXML
    private TableView<Song> songTableView;

    @FXML
    private TableColumn<Song, String> columnNameSong;

    @FXML
    private TableColumn<Song, String> columnGenderSong;


    @FXML
    private Button addAlbum;

    @FXML
    private Pane addAlbumPane;

    @FXML
    private Label resultOfAlbumRegistered;


    @FXML
    private Pane modifyAlbumPane;

    @FXML
    private ImageView albumImageViewToBeModified;

    @FXML
    private Label nameAlbumToBeModified;

    @FXML
    private Label yearAlbumToBeModified;


    private Album albumToBeModified;

    private Image imageAlbum;

    private AlbumDAO albumDAO;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAlbumsOfArtist();
        setupTableSelectionListenerAlbum();
        setupTableSelectionListenerSong();


    }


    @FXML
    private void uploadImageAlbum() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);


        Stage stage = (Stage) imageViewAlbum.getScene().getWindow();


        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                imageAlbum = new Image(file.toURI().toString());
                imageViewAlbum.setImage(imageAlbum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showRegisterPanel() {
        addAlbumPane.setVisible(true);
        addAlbum.setText("Ocultar");


    }

    @FXML
    private void registerAlbum() throws IOException {

        String albumName = albumNameTextField.getText();
        int year = Integer.parseInt(yearOfReleaseTextField.getText());

        if (!albumName.isEmpty() && year != 0 && imageAlbum != null) {
            byte[] bytesOfImage = imageToBytes(imageAlbum);


            User user = UserSession.UserSession().getUserLoggedIn();
            if (user.getClass().equals(Artist.class)) {

                Artist artistToInsert = (Artist) user;

                Album albumToInsert = new Album(albumName, bytesOfImage, year, artistToInsert);

                albumDAO = new AlbumDAO(albumToInsert);

                if ((AlbumDAO.findByArtistNameAndAlbumId(artistToInsert).getId() == 0)) {
                    albumDAO.insert();
                    resultOfAlbumRegistered.setText("Album correctamente registrado");
                    resultOfAlbumRegistered.setStyle("-fx-text-fill: green;");

                    resultOfAlbumRegistered.setVisible(true);
                } else {
                    resultOfAlbumRegistered.setText("El nombre album ya se encuentra registrado");
                    resultOfAlbumRegistered.setStyle("-fx-text-fill: red;");
                    resultOfAlbumRegistered.setVisible(true);
                }


            }

        }


    }


    @FXML
    private void modifyData() throws IOException {
        if (albumToBeModified != null) {
            changeToModifyAlbum(albumToBeModified);
        }
    }

    /**
     * Changes the scene to the modifyAlbum area.
     *
     * @throws IOException If an error occurs while loading the admin view.
     */
    @FXML
    public void changeToModifyAlbum(Object object) throws IOException {
        changeScene(Scenes.MODIFYALBUM, mainPane, object);
    }

    private void showAlbumsOfArtist() {
        User user = UserSession.UserSession().getUserLoggedIn();
        Artist artistToRetrieveAlbums = (Artist) user;
        AlbumDAO albumDAO = new AlbumDAO();
        List<Album> albums = albumDAO.findByArtist(artistToRetrieveAlbums);

        ObservableList<Album> albumsToBeShown = FXCollections.observableArrayList(albums);

        albumTableView.setItems(albumsToBeShown);

        columnAlbumPhoto.setCellValueFactory(cellData -> {
            byte[] imageInBytes = cellData.getValue().getImage();
            Image image = bytesToImage(imageInBytes);

            SimpleObjectProperty<ImageView> imageView = null;
            if (image != null) {


                ImageView imageViewToModify = new ImageView(image);
                imageViewToModify.setFitWidth(50);
                imageViewToModify.setFitHeight(50);

                imageView = new SimpleObjectProperty<>(imageViewToModify);

            }

            return imageView;
        });

        columnAlbumName.setCellValueFactory(cellData -> {
            Album album = cellData.getValue();
            String name = album.getName();
            return new SimpleStringProperty(name);
        });


    }




    private void setupTableSelectionListenerAlbum() {
        albumTableView.setOnMouseClicked(event -> {

            modifyAlbumPane.setVisible(true);
            Album selectedAlbum = albumTableView.getSelectionModel().getSelectedItem();

            if (selectedAlbum != null) {
                albumToBeModified = selectedAlbum;
                updateAlbumDetails();
                showSongsOfAlbum(selectedAlbum);
            }
        });
    }

    private void setupTableSelectionListenerSong() {
        songTableView.setOnMouseClicked(event -> {

            Song songSelected = songTableView.getSelectionModel().getSelectedItem();

            if (songSelected != null) {
                startSong(songSelected.getSongFile());
                updateAlbumDetails();
            }
        });
    }

    private void startSong(byte[] songFile) {


        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(songFile);


        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);


            clip.start();


            while (clip.isRunning()) {
                Thread.sleep(1000);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showSongsOfAlbum(Album album) {

        List<Song> songs = album.getSongsOfAlbum();

        ObservableList<Song> songsObservableList = FXCollections.observableArrayList(songs);

        songTableView.setItems(songsObservableList);

        columnNameSong.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            return new SimpleStringProperty(song.getName());
        });
        columnGenderSong.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            return new SimpleStringProperty(song.getMusicalGender());
        });

    }

    private void updateAlbumDetails() {
        if (albumToBeModified != null) {

            nameAlbumToBeModified.setText(albumToBeModified.getName());
            yearAlbumToBeModified.setText(String.valueOf(albumToBeModified.getYear()));

            byte[] imageBytes = albumToBeModified.getImage();
            Image albumImage = bytesToImage(imageBytes);
            albumImageViewToBeModified.setImage(albumImage);
        }
    }



}
