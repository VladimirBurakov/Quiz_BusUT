package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.dao.model.User;
import sample.globalconstants.FileConst;
import sample.services.CurrentUserDataSaver;
import sample.services.FXMLHelper;

public class ResultBoxController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okId;

    @FXML
    private Label messageLabel;

    @FXML
    private Label resultLabel;

    @FXML
    void initialize() {
        CurrentUserDataSaver.saveCurrentUserToDataBase(); //сохрание результата теста
        User currentUser = CurrentUserDataSaver.getCurrentUser();
        this.messageLabel.setText("Ваш результат:");
        String result = currentUser.getRightAnswersAmount() + " из " + currentUser.getQuestionsAmount();
        resultLabel.setText(result);
    }

    @FXML
   void toStat(ActionEvent event){
        okId.getScene().getWindow().hide();
        Stage stage = FXMLHelper.loadPage(FileConst.STAT_PAGE);
        stage.setMinWidth(450);
        stage.setMinHeight(350);
        stage.setTitle("Статистка");
    }
}
