package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.dao.SongDAO;
import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Song;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.ConvertBytes.*;
import static com.github.JoseAngelGiron.utils.SelectFileChooser.selectWAV;
import static com.github.JoseAngelGiron.utils.SelectFileChooser.selectPhoto;
import static com.github.JoseAngelGiron.view.AppController.changeScene;

public class ModifyAlbumController  extends Controller implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView imageViewAlbum;

    @FXML
    private Label albumNameLabel;

    @FXML
    private Label yearOfReleaseLabel;


    @FXML
    private TableView<Song> tableOfSongs;

    @FXML
    private TableColumn<Song, ImageView> columnPhotoSong;

    @FXML
    private TableColumn<Song, String> columnNameSong;

    @FXML
    private TableColumn<Song, String> columnGenderSong;



    @FXML
    private Pane paneSong;

    @FXML
    private ImageView imageSong;

    @FXML
    private TextField nameSongField;
    @FXML
    private TextField genderSongField;

    @FXML
    private Label labelSongAdd;

    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;


    private File fileSong;
    private File fileImage;
    private Song selectedSong;



    private Album albumToBeModified;
    private ObservableList<Song> songsOfTheAlbum;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        albumToBeModified = (Album) input;
        showAlbumData();

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableSelectionListenerSong();

    }

    @FXML
    public void changeToArtistOptions() throws IOException {
        changeScene(Scenes.ARTISTOPTIONS, mainPane, null);
    }

    @FXML
    public void setPaneSongVisible(){
        paneSong.setVisible(true);
    }

    @FXML
    public void storageSong() throws IOException {
        fileSong = selectWAV(new Stage());

        if(fileSong!=null){
            labelSongAdd.setText("Canción aceptada");
            labelSongAdd.setStyle("-fx-text-fill: green;");
            labelSongAdd.setVisible(true);
        }
    }

    @FXML
    public void storagePhotoSong() throws IOException {
        fileImage = selectPhoto(new Stage());
        showImageSong();

        if(fileImage!=null){
            labelSongAdd.setText("Imagen aceptada");
            labelSongAdd.setStyle("-fx-text-fill: green;");
            labelSongAdd.setVisible(true);
        }
    }

    private void showImageSong() {
        if (fileImage != null) {
            Image image = new Image(fileImage.toURI().toString());
            imageSong.setImage(image);
        } else {
            imageSong.setImage(null);
        }
    }

    @FXML
    public void save(){
        String nameSong = nameSongField.getText();
        String genderSong = genderSongField.getText();

        if(!nameSong.isEmpty()  && !genderSong.isEmpty() && fileSong !=null && fileImage!=null){
            byte[] songInBytes = fileToByte(fileSong);
            byte[] imageInBytes = fileToByte(fileImage);
            if(songInBytes!=null){

                Song songToBeInserted = new Song(nameSong, songInBytes, imageInBytes, genderSong, albumToBeModified);
                SongDAO songDAO = new SongDAO(songToBeInserted);
                songDAO.save();
                paneSong.setVisible(false);


            }

        }else{

            labelSongAdd.setText("Rellene todos los campos");
            labelSongAdd.setStyle("-fx-text-fill: red;");
            labelSongAdd.setVisible(true);
        }

    }

    @FXML
    public void update(){

        //A selected song, le añades los atributos de arriba

    }

    @FXML
    public void delete(){

        //comparte lógica con update, pero borra igual

    }

    private void setupTableSelectionListenerSong() {
        tableOfSongs.setOnMouseClicked(event -> {

            selectedSong = tableOfSongs.getSelectionModel().getSelectedItem();

            if (selectedSong != null) {

                updateButton.setVisible(true);
                deleteButton.setVisible(true);
                /**imageSong.setImage(bytesToImage(selectedSong.getPhotoSong()));
                byte[] imageInBytes = selectedSong.getPhotoSong();
                nameSongField.setText(selectedSong.getName());
                genderSongField.setText(selectedSong.getMusicalGender());
                byte[] songInBytes = selectedSong.getSongFile();**/



            }
        });
    }



    private void showAlbumData(){
        Image image = bytesToImage(albumToBeModified.getImage());
        imageViewAlbum.setImage(image);
        albumNameLabel.setText(albumToBeModified.getName());
        yearOfReleaseLabel.setText(String.valueOf(albumToBeModified.getYear()));

        songsOfTheAlbum = FXCollections.observableArrayList(albumToBeModified.getSongsOfAlbum());
        tableOfSongs.setItems(songsOfTheAlbum);

        columnPhotoSong.setCellValueFactory(cellData -> {
            Song song = cellData.getValue();
            Image imageSong = bytesToImage(song.getPhotoSong());
            ImageView imageView = new ImageView(imageSong);
            imageView.setFitWidth(100);
            imageView.setFitHeight(75);
            return new SimpleObjectProperty<>(imageView);
        });

        columnNameSong.setCellValueFactory(cellData -> {
            Song album = cellData.getValue();
            String name = album.getName();
            return new SimpleStringProperty(name);
        });

        columnGenderSong.setCellValueFactory(cellData -> {
            Song album = cellData.getValue();
            String gender = album.getMusicalGender();
            return new SimpleStringProperty(gender);
        });


    }
}
