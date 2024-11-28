package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.ListSongs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userProfileController extends Controller implements Initializable {
    @FXML
    private ImageView userImage;

    @FXML
    private Label Name;

    @FXML
    private int AmountOfLists;

    @FXML
    private TableView<ListSongs> listSongsTableView;

    @FXML
    private TableColumn<String, ListSongs> nameListColumn;

    @FXML
    private TableColumn<ImageView, ListSongs> photoListColumn;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
