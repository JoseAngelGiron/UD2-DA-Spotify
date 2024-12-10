package com.github.JoseAngelGiron.view;


import com.github.JoseAngelGiron.model.SongPlayer;
import com.github.JoseAngelGiron.model.dao.AlbumDAO;
import com.github.JoseAngelGiron.model.dao.SongDAO;
import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.Song;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.dao.AlbumDAO.findArtistByAlbumID;
import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;
import static com.github.JoseAngelGiron.view.AppController.changeScene;

public class AlbumController extends Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView albumImageView;

    @FXML
    private Label titleAlbum;

    @FXML
    private ImageView artistImage;

    @FXML
    private Label artistName;

    @FXML
    private Label yearOfRelease;


    @FXML
    private TableView<Song> songsTableView;

    @FXML
    private TableColumn<Song, String> numberColumn;

    @FXML
    private TableColumn<Song, ImageView> photoColumn;

    @FXML
    private TableColumn<Song, String> titleColumn;

    @FXML
    private FlowPane albumsRelated;

    @FXML
    private TitledPane reproductor;
    @FXML
    private ImageView imageSong;

    private Song actualSong;


    private Album albumToShow;
    private Artist artistRelated;

    private SongDAO songDAO;
    private AlbumDAO albumDAO;

    private ObservableList<Song> songObservableList;
    private ObservableList<Album> AlbumsOfArtist;





    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        albumToShow = (Album) input;
        showSongs();
        showArtistData();
        showAlbumData();
        showAlbumsRelated();

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    private void showAlbumData(){
        titleAlbum.setText(albumToShow.getName());
        yearOfRelease.setText(String.valueOf(albumToShow.getYear()));
        albumImageView.setImage(bytesToImage(albumToShow.getImage()));
    }

    private void showArtistData(){
        int key = albumToShow.getId();
        artistRelated = findArtistByAlbumID(key);

        artistName.setText(artistRelated.getName());
        artistImage.setImage(bytesToImage(artistRelated.getPhoto()));




    }


    private void showSongs(){
        songDAO = new SongDAO();
        List<Song> songList = songDAO.findSongsByAlbumId(albumToShow.getId());
        songList.sort((song1, song2) -> song1.getName().compareToIgnoreCase(song2.getName()));
        songObservableList = FXCollections.observableArrayList(songList);

        songsTableView.setItems(songObservableList);


        numberColumn.setCellValueFactory(cellData -> {
            int index = songObservableList.indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });

        photoColumn.setCellValueFactory(cellData -> {

            ImageView imageView = new ImageView();


            byte[] imageBytes = cellData.getValue().getPhotoSong();
            Image image = bytesToImage(imageBytes);


            imageView.setImage(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);

            return new ReadOnlyObjectWrapper<>(imageView);
        });

        titleColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });


        songsTableView.setRowFactory(tableView -> {
            TableRow<Song> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    actualSong = row.getItem();

                    if (actualSong != null) {
                        try {
                            startSong();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        imageSong.setImage(bytesToImage(actualSong.getPhotoSong()));
                        reproductor.setExpanded(true);
                    }
                }
            });
            return row;
        });

    }



    private void showAlbumsRelated(){
        albumDAO = new AlbumDAO();
        List<Album> albumList = albumDAO.findListOfAlbumByArtistID(artistRelated.getId());

        AlbumsOfArtist = FXCollections.observableArrayList(albumList);


        albumsRelated.getChildren().clear();

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


            albumsRelated.getChildren().add(albumItem);
        }


        albumsRelated.setOrientation(Orientation.HORIZONTAL);


    }

    private void changeToAlbum(Album album) throws IOException {
        changeScene(Scenes.ALBUMVIEW, anchorPane, album);
    }
}
