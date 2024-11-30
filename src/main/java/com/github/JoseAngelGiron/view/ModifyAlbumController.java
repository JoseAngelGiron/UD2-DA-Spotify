package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.dao.SongDAO;
import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.ConvertBytes.*;

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
    private TableColumn<Song, String> columnNameSong;

    @FXML
    private TableColumn<Song, String> columnGenderSong;

    @FXML
    private Pane paneSong;

    @FXML
    private TextField nameSongField;
    @FXML
    private TextField genderSongField;

    @FXML
    private Label labelSongAdd;



    private File fileSong;

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


    }
    @FXML
    public void storageSong() throws IOException {
        fileSong = selectMP3(new Stage());

        if(fileSong!=null){
            labelSongAdd.setText("Canción aceptada");
            labelSongAdd.setStyle("-fx-text-fill: green;");
            labelSongAdd.setVisible(true);
        }
    }


    @FXML
    public void save(){
        String nameSong = nameSongField.getText();
        String genderSong = genderSongField.getText();

        if(!nameSong.isEmpty()  && !genderSong.isEmpty() && fileSong !=null){
            byte[] songInBytes = fileToByte(fileSong);
            if(songInBytes!=null){

                Song songToBeInserted = new Song(nameSong, songInBytes, genderSong, albumToBeModified);
                SongDAO songDAO = new SongDAO(songToBeInserted);
                songDAO.save();

            }

        }else{

            labelSongAdd.setText("Rellene todos los campos");
            labelSongAdd.setStyle("-fx-text-fill: red;");
            labelSongAdd.setVisible(true);
        }

    }

    private void showAlbumData(){
        Image image = bytesToImage(albumToBeModified.getImage());
        imageViewAlbum.setImage(image);
        albumNameLabel.setText(albumToBeModified.getName());
        yearOfReleaseLabel.setText(String.valueOf(albumToBeModified.getYear()));

        songsOfTheAlbum = FXCollections.observableArrayList(albumToBeModified.getSongsOfAlbum());
        tableOfSongs.setItems(songsOfTheAlbum);

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
