package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import sample.dao.CurrentTestData;
import sample.helper.CheckHelper;
import sample.helper.FXMLHelper;

public class Controller {

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
    private MenuItem addMenuItemId;

    @FXML
    private MenuItem editMenuItemId;

    @FXML
    private MenuItem removeMenuItemId;

    @FXML
    private MenuItem statMenuItemId;


    @FXML
    private TextField amountQuestionTextArea;

    @FXML
    private Label descriptionLabel;

    private Stage stage;
    @FXML
    void start(ActionEvent event) {
        String amountQuestion = amountQuestionTextArea.getText();
        if(CheckHelper.isRightRange(amountQuestion, 5, 50)){
            CurrentTestData.setQuestionQuantity(Integer.parseInt(amountQuestion));
            startButton.getScene().getWindow().hide();
            FXMLHelper.loadPage("/sample/views/registration.fxml");
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
        stage = FXMLHelper.loadPage("/sample/views/add.fxml");
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("Добавить");
    }

    @FXML
    void edit(ActionEvent event) {
        stage = FXMLHelper.loadPage("/sample/views/edit.fxml");
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("Редактировать");
    }

    @FXML
    void remove(ActionEvent event) {
        stage = FXMLHelper.loadPage("/sample/views/remove.fxml");
        stage.setMinWidth(660);
        stage.setMinHeight(550);
        stage.getScene().getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        stage.setTitle("Удалить");
    }
    @FXML
    void stat(ActionEvent event) {
        stage = FXMLHelper.loadPage("/sample/views/statPage.fxml");
        stage.setMinWidth(440);
        stage.setMinHeight(330);
    }

    @FXML
    void initialize() {
        /*startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Кнопка включена");
            }
        });*/
    }
}

