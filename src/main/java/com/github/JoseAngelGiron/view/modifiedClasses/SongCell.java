package com.github.JoseAngelGiron.view.modifiedClasses;

import com.github.JoseAngelGiron.model.entity.Song;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import java.io.ByteArrayInputStream;

public class SongCell extends ListCell<Song> {
    @Override
    public void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);


        if (empty || song == null) {
            setText(null);
            setGraphic(null);
        } else {

            VBox songVBox = new VBox(5);
            songVBox.setAlignment(Pos.TOP_CENTER);


            Image image = new Image(new ByteArrayInputStream(song.getPhotoSong()));
            ImageView songImageView = new ImageView(image);
            songImageView.setFitWidth(100);
            songImageView.setFitHeight(100);


            Text songNameText = new Text(song.getName());
            songNameText.setStyle("-fx-fill: white; -fx-font-size: 14px;");


            songVBox.getChildren().addAll(songImageView, songNameText);


            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER_LEFT);


            hBox.getChildren().add(songVBox);


            setGraphic(hBox);
        }
    }
}
