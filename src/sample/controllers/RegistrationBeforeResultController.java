package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dao.User;
import sample.services.CurrentUserDataSaver;
import sample.services.FXMLHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationBeforeResultController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameTextArea;

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
            User user = CurrentUserDataSaver.getCurrentUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            registrationButton.getScene().getWindow().hide();
            stage = FXMLHelper.loadPage("/sample/views/resultPage.fxml");
            stage.setMinWidth(300);
            stage.setMinHeight(200);
            stage.setTitle("Результат");

        }else{
            FXMLHelper.setMessage("Заполните поля!\n" +
                    "Это для вашего же блага,\n" +
                    "для анализа успешности прохождения.");
            FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
        }
    }

    @FXML
    void initialize() {
    }
}
