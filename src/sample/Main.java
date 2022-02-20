package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.*;
import sample.dao.model.Questions;
import sample.services.DataForTest;

import java.util.ArrayList;

public class Main extends Application {
        private static DataForTest dataForTest;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/registrationPage.fxml"));
        primaryStage.setTitle("Burakov Bas UT Test Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(320);
        primaryStage.getScene().getStylesheets().add(Main.class.getResource("/sample/stylesheets/global.css").toExternalForm());
        //root.getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        primaryStage.show();
    }

    public static ArrayList<Questions>list = new ArrayList<>();
    public static void main(String[] args) {
        dataForTest = new DataForTest(new FileDataHandler());
        launch(args);
    }
    public static DataForTest getDataForTest(){
        if(dataForTest == null){
            return dataForTest = new DataForTest(new FileDataHandler());
        }else{
            return  dataForTest;
        }
    }

    public static void setDataForTest(DataForTest dataForTest){
        Main.dataForTest = dataForTest;

    }

}
