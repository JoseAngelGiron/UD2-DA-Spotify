package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.dao.AlbumDAO;
import com.github.JoseAngelGiron.model.entity.Album;
import com.github.JoseAngelGiron.model.entity.Artist;
import com.github.JoseAngelGiron.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.ConvertBytes.assignImage;
import static com.github.JoseAngelGiron.utils.ConvertBytes.imageToBytes;

public class ArtistOptionsController extends Controller implements Initializable {

    @FXML
    private ImageView imageViewAlbum;

    @FXML
    private TextField albumNameTextField;

    @FXML
    private TextField yearOfReleaseTextField;




    private Image imageAlbum;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void uploadImageAlbum(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);


        Stage stage = (Stage) imageViewAlbum.getScene().getWindow();


        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            try {
                imageAlbum = new Image(file.toURI().toString());
                imageViewAlbum.setImage(imageAlbum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void registerAlbum() throws IOException {
        String albumName = albumNameTextField.getText();
        int year = Integer.parseInt(yearOfReleaseTextField.getText());


        if( !albumName.isEmpty() && year!=0 && imageAlbum!=null){
            byte[] bytesOfImage = imageToBytes(imageAlbum);

            User user = UserSession.UserSession().getUserLoggedIn();
            if(user.getClass().equals(Artist.class)){
                Artist artistToInsert = (Artist) user;
                Album albumToInsert =new Album( albumName, bytesOfImage, year, artistToInsert );
                AlbumDAO albumDAO = new AlbumDAO(albumToInsert);
                albumDAO.insert();
            }

        }




    }
}
