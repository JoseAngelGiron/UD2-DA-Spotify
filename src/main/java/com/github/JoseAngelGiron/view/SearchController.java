package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.SongPlayer;
import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.SongDAO;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.SongDAO.findSongAlbumAndArtist;
import static com.github.JoseAngelGiron.model.dao.UserDAO.insertIntoSearch;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.view.AppController.changeScene;

public class SearchController extends Controller implements Initializable {

    private Instant lastClickTime = Instant.now().minusSeconds(1);

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

    @FXML
    private Label result;

    @FXML
    private FlowPane flowPaneSongs;

    private ObservableList<Song> observableListOfSongs;
    private Song actualSong;
    private List<Song> listOfSongs;
    private List<Song> allSongs;





    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        SongDAO songDAO = new SongDAO();
        if(input!=null){
            String search = (String) input;
            listOfSongs = findSongAlbumAndArtist(search);
            allSongs = songDAO.findSongsByName(search);

            if(listOfSongs.isEmpty()){
                result.setText("No se han encontrado resultados");
                result.setStyle("-fx-text-fill: red;");
            }else{
                result.setText("Resultados principales");
                showData();
                showResults();
            }

        }else{
            result.setText("No se han encontrado resultados");
            result.setStyle("-fx-text-fill: red;");
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

            SongDAO songDAO = new SongDAO(actualSong);
            songDAO.updateNumberOfPlays();
            SongPlayer.playSong(actualSong.getSongFile());
        }
    }

    @FXML
    public void stopSong(){
        SongPlayer.stopSong();

    }

    public void changeArtistProfile(Artist artist) throws IOException {
        changeScene(Scenes.ARTISTPROFILE, mainPane, artist);
    }

    private void showData(){

        observableListOfSongs = FXCollections.observableArrayList(listOfSongs);
        tableViewOfSongs.setItems(observableListOfSongs);

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
                            //System.out.println("Artista seleccionado: " + artist.getName()); // Mostrar información del artista
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
                            Instant now = Instant.now();


                            if (Duration.between(lastClickTime, now).toMillis() < 2000) {
                                return;
                            }
                            lastClickTime = now;

                            actualSong = song;
                            try {
                                startSong();
                                insertIntoSearch(UserSession.UserSession().getUserLoggedIn().getId(), actualSong.getId());
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

    private void showResults() {

        flowPaneSongs.getChildren().clear();

        if (allSongs != null && !allSongs.isEmpty()) {

            for (Song song : allSongs) {
                VBox songBox = new VBox(10);
                songBox.setStyle("-fx-alignment: center; -fx-padding: 10;");


                ImageView songImageView = new ImageView(bytesToImage(song.getPhotoSong()));
                songImageView.setFitWidth(100);
                songImageView.setFitHeight(100);


                Label songLabel = new Label(song.getName());
                songLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");


                songBox.getChildren().addAll(songImageView, songLabel);


                songBox.setUserData(song);


                flowPaneSongs.getChildren().add(songBox);


                songBox.setOnMouseClicked(event -> {

                    Instant now = Instant.now();
                    if (Duration.between(lastClickTime, now).toMillis() < 2000) {
                        return;
                    }
                    lastClickTime = now;

                    Song selectedSong = (Song) songBox.getUserData();
                    if (selectedSong != null) {
                        actualSong = selectedSong;
                        try {
                            startSong();
                            insertIntoSearch(UserSession.UserSession().getUserLoggedIn().getId(), actualSong.getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imageSong.setImage(bytesToImage(selectedSong.getPhotoSong()));
                        reproductor.setExpanded(true);
                    }
                });
            }
        }
    }

}
