package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.dao.TestDataManager;
import sample.dao.User;
import sample.db.UsersDatabaseHandler;
import sample.helper.FXMLHelper;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameTextArea;

    @FXML
    private Button registrationButton;

    @FXML
    private Label firstAndSecondNameLabel;

    @FXML
    private TextField secondNameTextArea;

    @FXML
    private Label descriptionLabel;

    @FXML
    void initialize() {
       signUpNewUser();
    }

    private void signUpNewUser(){
        UsersDatabaseHandler dbHandler = new UsersDatabaseHandler();
        registrationButton.setOnAction(event -> {
            String firstName = firstNameTextArea.getText().trim();
            String lastName = secondNameTextArea.getText().trim();
            int questionsAmount = TestDataManager.getQuestionQuantity();
            int rightAnswersAmount = TestDataManager.getScore();
            if(!firstName.isEmpty() && !lastName.isEmpty()){
                User user = new User(firstName, lastName, questionsAmount, rightAnswersAmount);
                TestDataManager.setCurrentUser(user);
                dbHandler.saveName(user);
                registrationButton.getScene().getWindow().hide();
                FXMLHelper.loadPage("/sample/views/BasUT.fxml");
            }else{
                FXMLHelper.setMessage("Заполните поля!\n" +
                               "Это для вашего же блага,\n" +
                                "для анализа успешности прохождения.");
                FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
            }
        });
    }

}
