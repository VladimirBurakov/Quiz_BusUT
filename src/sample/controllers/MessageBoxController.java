package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.helper.FXMLHelper;

public class MessageBoxController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private Label messageLabel;


    @FXML
    void initialize() {
        messageLabel.setText(FXMLHelper.getMessage());
        okButton.setOnAction(event -> {
            okButton.getScene().getWindow().hide();
            //messageLabel.setText("");
        });
    }
}

