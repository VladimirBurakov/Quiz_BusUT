package sample.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLHelper {
    private static String message;
    public static Stage loadPage(String fileNameRoot){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLHelper.class.getResource(fileNameRoot));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(150);
        stage.setMinWidth(250);
        stage.getScene().getStylesheets().add(FXMLHelper.class.getResource("/sample/stylesheets/global.css").toExternalForm());
        stage.show();
        return stage;
    }
    public static void setMessage(String message){
        FXMLHelper.message = message;
    }
    public static String getMessage(){
        return message;
    }
}
