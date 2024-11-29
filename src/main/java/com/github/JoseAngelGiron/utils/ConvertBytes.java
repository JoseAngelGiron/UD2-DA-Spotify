package com.github.JoseAngelGiron.utils;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ConvertBytes {

    private final static long MAXSIZEINMB = 16;

    /**
     * Converts a file to a byte array.
     *
     * @param archivo The file to be converted to a byte array.
     * @return A byte array representing the contents of the file.
     */
    public static byte[] fileToByte(File archivo) {

        byte[] bytesPNG = null;

        try (FileInputStream fis = new FileInputStream(archivo)) {
            bytesPNG = new byte[(int) archivo.length()];
            int bytesRead = fis.read(bytesPNG);
            if (bytesRead != archivo.length()) {
                System.err.println("No se leyeron todos los bytes del archivo");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytesPNG;
    }


    /**
     * Assigns an image file to be used as an icon for the game.
     *
     * @param event The ActionEvent triggered by the user.
     * @return The selected image file, or null if no file is selected or the file exceeds the maximum size.
     */
    public static File assignImage(ActionEvent event){
        File fileToReturn;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        fileToReturn = fileChooser.showOpenDialog(stage);

        if (fileToReturn != null) {
            long fileSizeInMB = fileToReturn.length() / 1024 / 1024;
            if (fileSizeInMB > MAXSIZEINMB) {
                fileToReturn = null;
            }
        }
        return fileToReturn;
    }

    /**
     * Converts a byte array to a JavaFX Image object.
     *
     * @param bytes The byte array representing the image data.
     * @return The JavaFX Image object created from the byte array. Returns null if the byte array is null or empty.
     */
    public static Image bytesToImage(byte[] bytes) {
        Image imageToReturn;
        ByteArrayInputStream bis;
        if (bytes == null || bytes.length == 0) {
            imageToReturn = null;
        }else{
            bis = new ByteArrayInputStream(bytes);
            imageToReturn = new Image(bis);
        }
        return imageToReturn;

    }

    public static byte[] imageToBytes(Image image) throws IOException {
        // Crear un BufferedImage con las mismas dimensiones que la imagen de JavaFX
        BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Usar PixelReader para obtener los píxeles de la imagen JavaFX
        PixelReader pixelReader = image.getPixelReader();

        // Leer píxeles y establecerlos en el BufferedImage
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                int argb = (int) (color.getRed() * 255) << 16 | (int) (color.getGreen() * 255) << 8 | (int) (color.getBlue() * 255) | (int) (color.getOpacity() * 255) << 24;
                bufferedImage.setRGB(x, y, argb);
            }
        }

        // Escribir el BufferedImage en un ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);  // Cambiar "jpg" a "png" si prefieres PNG

        // Retornar el byte[]
        return byteArrayOutputStream.toByteArray();
    }


}
