package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.SongPlayer;
import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.view.modifiedClasses.SongCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.SongPlayer.playSong;
import static com.github.JoseAngelGiron.model.SongPlayer.stopSong;
import static com.github.JoseAngelGiron.model.dao.ArtistDAO.findMostPopularArtists;
import static com.github.JoseAngelGiron.model.dao.UserDAO.findLastSongsSearched;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;


public class HomeController extends Controller implements Initializable {

    @FXML
    private TableView<Artist> artistTableView;

    @FXML
    private TableColumn<Artist, ImageView> photoArtistTableColumn;

    @FXML
    private TableColumn<Artist, String> nameArtistTableColumn;

    @FXML
    private TableColumn<Artist, Integer> totalOfPlays;

    @FXML
    private FlowPane songFlowPane;

    @FXML
    private TitledPane reproductor;

    @FXML
    private ImageView imageSong;

    private Song actualSong;



    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showData();
        showLatestSongs();
        setupSongListeners();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stopSong();

    }

    @FXML
    public void stopSong(){
        SongPlayer.stopSong();

    }

    @FXML
    public void startSong() throws InterruptedException {
        if(actualSong!=null){
            SongPlayer.playSong(actualSong.getSongFile());
        }
    }

    private void showData() {

        List<Artist> artists = findMostPopularArtists();


        artistTableView.getItems().clear();

        ObservableList<Artist> artistsList = FXCollections.observableArrayList(artists);
        artistTableView.setItems(artistsList);

        photoArtistTableColumn.setCellValueFactory(cellData -> {
            Artist artist = cellData.getValue();
            Image image = bytesToImage(artist.getPhoto());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            return new SimpleObjectProperty<>(imageView);
        });


        nameArtistTableColumn.setCellValueFactory(cellData -> {
            Artist artist = cellData.getValue();
            return new SimpleStringProperty(artist.getName());
        });
        nameArtistTableColumn.setStyle("-fx-alignment: CENTER;");

        totalOfPlays.setCellValueFactory(cellData -> {
            Artist artist = cellData.getValue();
            return new SimpleIntegerProperty(artist.getTotalPlays()).asObject();
        });
        totalOfPlays.setStyle("-fx-alignment: CENTER;");
    }


    private void showLatestSongs() {
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        List<Song> songList = findLastSongsSearched(currentUser.getId());

        ObservableList<Song> observableSongs = FXCollections.observableArrayList(songList);

        songFlowPane.getChildren().clear();

        for (Song song : observableSongs) {

            VBox songBox = new VBox(10);
            songBox.setStyle("-fx-alignment: center; -fx-padding: 10;");


            ImageView songImageView = new ImageView(bytesToImage(song.getPhotoSong()));
            songImageView.setFitWidth(100);
            songImageView.setFitHeight(100);


            Label songLabel = new Label(song.getName());
            songLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");


            songBox.getChildren().addAll(songImageView, songLabel);


            songBox.setUserData(song);


            songFlowPane.getChildren().add(songBox);
        }
    }


    private void setupSongListeners() {
        for (Node node : songFlowPane.getChildren()) {
            node.setOnMouseClicked(event -> {
                if (node instanceof VBox) {
                    VBox songBox = (VBox) node;


                    actualSong = (Song) songBox.getUserData();

                    if (actualSong != null) {
                        playSong(actualSong.getSongFile());
                    }
                    imageSong.setImage(bytesToImage(actualSong.getPhotoSong()));
                    reproductor.setExpanded(true);
                }
            });
        }
    }
}



