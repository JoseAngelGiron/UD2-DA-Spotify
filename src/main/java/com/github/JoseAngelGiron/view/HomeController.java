package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Artist;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.SongPlayer.stopSong;
import static com.github.JoseAngelGiron.model.dao.ArtistDAO.findMostPopularArtists;
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



    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showData();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stopSong();

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
            imageView.setFitWidth(100);
            imageView.setFitHeight(50);
            return new SimpleObjectProperty<>(imageView);
        });


        nameArtistTableColumn.setCellValueFactory(cellData -> {
            Artist artist = cellData.getValue();
            return new SimpleStringProperty(artist.getName());
        });

        totalOfPlays.setCellValueFactory(cellData -> {
            Artist artist = cellData.getValue();
            return new SimpleIntegerProperty(artist.getTotalPlays()).asObject();
        });
    }



}
