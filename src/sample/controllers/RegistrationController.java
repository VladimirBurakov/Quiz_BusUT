package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.services.CurrentUserDataSaver;
import sample.dao.model.User;
import sample.services.FXMLHelper;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameTextArea;

    @FXML
    private Button skipButton;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField secondNameTextArea;
    private Stage stage;

    @FXML
    void registration(ActionEvent event) {
        String firstName = firstNameTextArea.getText().trim();
        String lastName = secondNameTextArea.getText().trim();
        if(!firstName.isEmpty() && !lastName.isEmpty()){
            User user = new User(firstName, lastName);
            CurrentUserDataSaver.setCurrentUser(user);
            registrationButton.getScene().getWindow().hide();
            stage = FXMLHelper.loadPage("/sample/views/startPage.fxml");
            stage.setMinWidth(450);
            stage.setMinHeight(350);
            stage.setTitle("Старт теста");
        }else{
            FXMLHelper.setMessage("Заполните поля!\n" +
                    "Это для вашего же блага,\n" +
                    "для анализа успешности прохождения.");
            FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
        }
    }

    @FXML
    void skipRegistration(ActionEvent event) {
        String firstName = "";
        String lastName = "";
        User user = new User(firstName, lastName);
        CurrentUserDataSaver.setCurrentUser(user);
        skipButton.getScene().getWindow().hide();
        stage = FXMLHelper.loadPage("/sample/views/startPage.fxml");
        stage.setMinWidth(450);
        stage.setMinHeight(350);
        stage.setTitle("Старт теста");
    }

    @FXML
    void initialize() {
    }
}
