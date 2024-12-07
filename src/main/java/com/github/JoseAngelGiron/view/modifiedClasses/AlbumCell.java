package com.github.JoseAngelGiron.view.modifiedClasses;

import com.github.JoseAngelGiron.model.entity.Album;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.github.JoseAngelGiron.utils.ConvertBytes.bytesToImage;

public class AlbumCell extends ListCell<Album> {

    private HBox content;
    private ImageView imageView;
    private VBox textContainer;
    private Text title;
    private Text subtitle;

    public AlbumCell() {
        imageView = new ImageView();
        imageView.setFitWidth(100); // Tamaño de la imagen
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        title = new Text();
        title.setStyle("-fx-fill: white; -fx-font-size: 16;");

        subtitle = new Text();
        subtitle.setStyle("-fx-fill: gray; -fx-font-size: 12;");

        textContainer = new VBox(title, subtitle);
        textContainer.setSpacing(5);

        content = new HBox(imageView, textContainer);
        content.setSpacing(10);
        content.setPadding(new Insets(5));
    }

    @Override
    protected void updateItem(Album album, boolean empty) {
        super.updateItem(album, empty);
        if (empty || album == null) {
            setGraphic(null);
        } else {
            Image image = bytesToImage(album.getImage());
            imageView.setImage(image);
            title.setText(album.getName());
            //subtitle.setText(album.getSubtitle());
            setGraphic(content);
        }
    }
}
