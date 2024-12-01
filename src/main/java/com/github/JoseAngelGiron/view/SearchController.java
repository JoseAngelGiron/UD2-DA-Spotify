package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.SongPlayer;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.view.AppController.changeScene;

public class SearchController extends Controller implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private TableView<Song> tableViewOfSongs;

    @FXML
    private TableColumn<Song, ImageView> tableColumnArtistPhoto;

    @FXML
    private TableColumn<Song, String> tableColumnArtistName;

    @FXML
    private TableColumn<Song, ImageView> tableColumnAlbumPhoto;

    @FXML
    private TableColumn<Song, String> tableColumnAlbumName;

    @FXML
    private TableColumn<Song, ImageView> tableColumnSongPhoto;

    @FXML
    private TableColumn<Song, String> tableColumnSongName;

    @FXML
    private TitledPane reproductor;

    @FXML
    private ImageView imageSong;

    private ObservableList<Song> observableListOfSongs;
    private Song actualSong;



    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        if(input!=null){
            ArrayList<Song> games = (ArrayList<Song>) input;
            this.observableListOfSongs = FXCollections.observableArrayList(games);
            this.tableViewOfSongs.setItems(this.observableListOfSongs);
            showData();
        }



    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    public void startSong() throws InterruptedException {
        if(actualSong!=null){
            SongPlayer.playSong(actualSong.getSongFile());
        }
    }

    @FXML
    public void stopSong(){
        SongPlayer.stopSong();

    }

    public void changeArtistProfile(Artist artist) throws IOException {
        changeScene(Scenes.ARTISTPROFILE, mainPane,artist);
    }

    private void showData(){

        tableColumnArtistPhoto.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView();

            imageView.setImage(bytesToImage(cellData.getValue().getAlbum().getArtist().getPhoto()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);
        });

        tableColumnArtistPhoto.setCellFactory(column -> new javafx.scene.control.TableCell<Song, ImageView>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    setGraphic(imageView);
                    imageView.setImage(item.getImage());
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);


                    setOnMouseClicked(event -> {
                        Song song = getTableView().getItems().get(getIndex());
                        if (song != null) {

                            var artist = song.getAlbum().getArtist();
                            try {
                                changeArtistProfile(artist);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Artista seleccionado: " + artist.getName()); // Mostrar información del artista
                        }
                    });
                }
            }
        });


        tableColumnArtistName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAlbum().getArtist().getName()));


        tableColumnAlbumPhoto.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView();

            imageView.setImage(bytesToImage(cellData.getValue().getAlbum().getImage()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);
        });


        tableColumnAlbumName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAlbum().getName()));


        tableColumnSongPhoto.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView();
            imageView.setImage(bytesToImage(cellData.getValue().getPhotoSong()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);
        });

        tableColumnSongPhoto.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    setGraphic(imageView);
                    imageView.setImage(item.getImage());
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);


                    setOnMouseClicked(event -> {
                        Song song = getTableView().getItems().get(getIndex());
                        if (song != null) {
                            actualSong = song;
                            try {
                                startSong();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            imageSong.setImage(bytesToImage(song.getPhotoSong()));
                            reproductor.setExpanded(true);
                        }
                    });
                }
            }
        });



        tableColumnSongName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
    }
}
