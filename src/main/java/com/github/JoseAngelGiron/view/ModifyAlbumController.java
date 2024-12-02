package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.dao.AlbumDAO;
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

import java.sql.SQLException;
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
    private TextField albumNameTextField;

    @FXML
    private TextField yearOfReleaseField;


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
    private Button registerSongButton;

    @FXML
    private Button deleteAlbumButton;
    @FXML
    private Button updateAlbumButton;

    @FXML
    private Button deleteSongButton;
    @FXML
    private Button updateSongButton;


    private File fileSong;
    private File fileImage;
    private Song selectedSong;

    private Album albumToBeModified;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;
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
        selectedSong = null;
        paneSong.setVisible(true);
        deleteSongButton.setVisible(false);
        updateSongButton.setVisible(false);

        nameSongField.setText("");
        genderSongField.setText("");
        imageSong.setImage(null);

    }

    @FXML
    public void storageSong() throws IOException {
        fileSong = selectWAV(new Stage());

        if(fileSong!=null){
            labelSongAdd.setText("Canción aceptada");
            labelSongAdd.setStyle("-fx-text-fill: green;");
            labelSongAdd.setVisible(true);
        }else{
            labelSongAdd.setText("introduzca un formato valido");
            labelSongAdd.setStyle("-fx-text-fill: red;");
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
    public void saveSong(){
        String nameSong = nameSongField.getText();
        String genderSong = genderSongField.getText();

        if(!nameSong.isEmpty()  && !genderSong.isEmpty() && fileSong !=null && fileImage!=null){
            byte[] songInBytes = fileToByte(fileSong);
            byte[] imageInBytes = fileToByte(fileImage);
            if(songInBytes!=null){

                Song songToBeInserted = new Song(nameSong, songInBytes, imageInBytes, genderSong, albumToBeModified);
                songDAO = new SongDAO(songToBeInserted);
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
    public void updateSong(){
        String nameSong = nameSongField.getText();
        String genderText = genderSongField.getText();

        if(selectedSong!=null && nameSong !=null && genderText != null && selectedSong.getSongFile() !=null && selectedSong.getPhotoSong()!=null){
            selectedSong.setAlbum(albumToBeModified);
            selectedSong.setName(nameSong);
            selectedSong.setMusicalGender(genderText);
            if(fileSong!=null){
                selectedSong.setSongFile(fileToByte(fileSong));
            }
            if(fileImage!=null){
                selectedSong.setPhotoSong(fileToByte(fileImage));
            }

            songDAO = new SongDAO(selectedSong);
            songDAO.save();

        }else{
            labelSongAdd.setText("Rellene todos los campos");
            labelSongAdd.setVisible(true);
        }
    }

    @FXML
    public void deleteSong() throws SQLException {

        if(selectedSong!=null){

            songDAO = new SongDAO(selectedSong);
            songDAO.delete();

        }else{
            labelSongAdd.setText("Canción borrada");
            labelSongAdd.setVisible(true);
        }
    }

    @FXML
    public void changeAlbumPhoto(){
        File file =selectPhoto(new Stage());
        byte[] imageInBytes = fileToByte(file);
        albumToBeModified.setImage(imageInBytes);
        imageViewAlbum.setImage(bytesToImage(imageInBytes));

    }

    @FXML
    public void updateAlbum(){
        String albumName = albumNameTextField.getText();
        int yearAlbum = Integer.parseInt(String.valueOf(yearOfReleaseField.getText()));

        albumToBeModified.setName(albumName);
        albumToBeModified.setYear(yearAlbum);

        albumDAO = new AlbumDAO(albumToBeModified);

        albumDAO.save();

    }

    @FXML
    public void deleteAlbum() throws SQLException, IOException, InterruptedException {

        albumDAO = new AlbumDAO(albumToBeModified);
        albumDAO.delete();
        Thread.sleep(5000);
        changeToArtistOptions();

    }

    private void setupTableSelectionListenerSong() {
        tableOfSongs.setOnMouseClicked(event -> {

            selectedSong = tableOfSongs.getSelectionModel().getSelectedItem();
            paneSong.setVisible(true);
            registerSongButton.setVisible(false);
            deleteSongButton.setVisible(true);
            updateSongButton.setVisible(true);

            if (selectedSong != null) {
                imageSong.setImage(bytesToImage(selectedSong.getPhotoSong()));
                nameSongField.setText(selectedSong.getName());
                genderSongField.setText(selectedSong.getMusicalGender());
            }


        });
    }

    private void showAlbumData(){
        Image image = bytesToImage(albumToBeModified.getImage());
        imageViewAlbum.setImage(image);
        albumNameTextField.setText(albumToBeModified.getName());
        yearOfReleaseField.setText(String.valueOf(albumToBeModified.getYear()));

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
