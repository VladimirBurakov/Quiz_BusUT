package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.ExcelDataHandler;
import sample.dao.FileDataHandler;
import sample.dao.JsonFileDataHandler;
import sample.dao.MySQLDataHandler;
import sample.globalconstants.FileConst;
import sample.services.CurrentUserDataSaver;
import sample.services.CheckHelper;
import sample.services.DataForTest;
import sample.services.FXMLHelper;

public class StartController {

    private Stage stage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label amountQuestionLabel;

    @FXML
    private TilePane settingsImageButton;

    @FXML
    private Button startButton;

    @FXML
    private TextField amountQuestionTextArea;

    @FXML
    private Label descriptionLabel;

    @FXML
    void start(ActionEvent event) {
        String amountQuestion = amountQuestionTextArea.getText();
        if(CheckHelper.isRightRange(amountQuestion, 5, 50)){
            CurrentUserDataSaver.getCurrentUser().setQuestionsAmount(Integer.parseInt(amountQuestion));
            startButton.getScene().getWindow().hide();
            stage = FXMLHelper.loadPage(FileConst.TEST_PAGE);
            stage.setMinWidth(660);
            stage.setMinHeight(550);
            stage.setTitle("Test");
        }
    }

    @FXML
    void exitStart(KeyEvent event) {
       /* if(event.getCode() == KeyCode.ESCAPE){
            Scene scene = ((Node)event.getSource()).getScene();
            scene.getWindow().hide();
        }*/
    }

    @FXML
    void add(ActionEvent event) {
        stage = FXMLHelper.loadPage(FileConst.ADD_PAGE);
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        //stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("????????????????");
    }

    @FXML
    void edit(ActionEvent event) {
        stage = FXMLHelper.loadPage(FileConst.EDIT_PAGE);
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        //stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("??????????????????????????");
    }

    @FXML
    void remove(ActionEvent event) {
        stage = FXMLHelper.loadPage(FileConst.REMOVE_PAGE);
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        //stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("??????????????");
    }

    @FXML
    void stat(ActionEvent event) {
        stage = FXMLHelper.loadPage(FileConst.STAT_PAGE);
        stage.setMinWidth(450);
        stage.setMinHeight(350);
        stage.setTitle("????????????????????");
    }

    @FXML
    void initialize() {

    }

    @FXML
    void fileSource(ActionEvent event) {
        Main.setDataForTest(new DataForTest(new FileDataHandler()));
        FXMLHelper.setMessage("???????????? ???????????????? ????\n" +
                "???????????? \".csv\"");
        FXMLHelper.loadPage(FileConst.MESSAGE_WINDOWS);
    }

    @FXML
    void jsonSource(ActionEvent event) {
        Main.setDataForTest(new DataForTest(new JsonFileDataHandler()));
        FXMLHelper.setMessage("???????????? ???????????????? ????\n" +
                "???????????? \".json\"");
        FXMLHelper.loadPage(FileConst.MESSAGE_WINDOWS);
    }

    @FXML
    void mysqlSource(ActionEvent event) {
        Main.setDataForTest(new DataForTest(new MySQLDataHandler()));
        FXMLHelper.setMessage("???????????? ???????????????? ????\n" +
                "???????? ???????????? \"MySQL\"");
        FXMLHelper.loadPage(FileConst.MESSAGE_WINDOWS);
    }

    @FXML
    void excelSource(ActionEvent event) {
        Main.setDataForTest(new DataForTest(new ExcelDataHandler()));
        FXMLHelper.setMessage("???????????? ???????????????? ????\n" +
                "?????????? \".xslx\"");
        FXMLHelper.loadPage(FileConst.MESSAGE_WINDOWS);
    }
}

