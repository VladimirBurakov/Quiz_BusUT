package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.dao.TestDataManager;
import sample.dao.User;
import sample.db.UsersDatabaseHandler;

public class ResultBoxController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label resultLabel;

    @FXML
    void initialize() {
        UsersDatabaseHandler dbHandler = new UsersDatabaseHandler();
        User user = TestDataManager.getCurrentUser();
        user.setRightAnswersAmount(TestDataManager.getScore());
        dbHandler.saveResult(user); //сохрание результата теста

        this.messageLabel.setText("Ваш результат:");
        String result = TestDataManager.getScore() + " из " + TestDataManager.getQuestionQuantity();
        resultLabel.setText(result);
        okButton.setOnAction(event -> {
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });
    }

}
