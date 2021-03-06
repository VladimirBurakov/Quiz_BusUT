package sample.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.model.Questions;
import sample.services.FXMLHelper;

public class EditController extends AbstractDataController {
    @FXML
    private AnchorPane allWindowId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField questionNumberTextFieldId;

    @FXML
    private Label testTitleLabel;

    @FXML
    private TextArea firstTextAreaAnswerId;

    @FXML
    private Button searchButtonId1;

    @FXML
    private TextArea thirdTextAreaAnswerId;

    @FXML
    private TextArea fifthTextAreaAnswerId;

    @FXML
    private TextArea sixthTextAreaAnswerId;

    @FXML
    private TextField rightAnswerTextFieldId;

    @FXML
    private Button backButtonId;

    @FXML
    private Button exitButtonId;

    @FXML
    private TextArea fourthTextAreaAnswerId;

    @FXML
    private Button saveButtonId;

    @FXML
    private TextField questionNumberBigTextFieldId;

    @FXML
    private Button forwardButtonId;

    @FXML
    private TextArea secondTextAreaAnswerId;

    @FXML
    private TextArea questionTextAreaId;

     void setData(Questions question){
        //Обнуление полей ответов
        for(TextArea textArea: textAreas){
            textArea.setText("");
        }
        //получение номера вопроса
        questionNumberTextFieldId.setText(question.getNumber());

        //получение вопроса
        questionTextAreaId.setText(question.getQuestion());

        //перебор массива String[] в Questions объекте и получение полей вариантов ответа
        int arrayQuestionAnswersLength = question.getAnswers().length;
        for (int i = 0; i < arrayQuestionAnswersLength; i++) {
            switch (i){
                case 0: firstTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
                case 1: secondTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
                case 2: thirdTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
                case 3: fourthTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
                case 4: fifthTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
                case 5: sixthTextAreaAnswerId.setText(question.getAnswers()[i]);
                    break;
            }
        }

        // получение правильного ответа
        rightAnswerTextFieldId.setText(String.valueOf(question.getResult()));
    }
     void readerFromForm(){
        Questions question = list.get(counter);
        question.setNumber(questionNumberTextFieldId.getText());
        question.setQuestion(questionTextAreaId.getText());
        String [] array = new String[6];
        for(int i = 0; i < array.length; i++){
            array[i] = textAreas[i].getText() != null ? textAreas[i].getText() : "";
        }
        question.setAnswers(array);
        question.setResult(Integer.parseInt(rightAnswerTextFieldId.getText()));
    }

    @FXML
    void exitSettings(ActionEvent event) {
        Stage stage = (Stage) exitButtonId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void back(ActionEvent event) {
        int temp = counter - 1;
        if(temp >= 0){
            counter--;
            setData(list.get(counter));
        }else{
            counter = list.size() - 1;
            setData(list.get(counter));
        }
    }

    @FXML
    void forward(ActionEvent event) {
        int temp = counter + 1;
        if(temp < list.size()){
            counter++;
            setData(list.get(counter));
        }else{
            counter = 0;
            setData(list.get(counter));
        }
    }

    @FXML
    void search(ActionEvent event) {
        String number = questionNumberBigTextFieldId.getText();
        boolean exist = false;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getNumber().startsWith(number)){
                setData(list.get(i));
                counter = i;
                exist = true;
                break;
            }
        }
        if(!exist){
            FXMLHelper.setMessage("Такого номера вопроса\n" +
                    "не существует!");
            FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
        }

    }

    @FXML
    void initialize() {
        textAreas = new TextArea[]{firstTextAreaAnswerId, secondTextAreaAnswerId, thirdTextAreaAnswerId, fourthTextAreaAnswerId, fifthTextAreaAnswerId, sixthTextAreaAnswerId};
        list = Main.getDataForTest().getDataHandler().getAllData();
        setData(list.get(0));

        forwardButtonId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.RIGHT){
                    forwardButtonId.fire();
                    Platform.runLater( () -> forwardButtonId.requestFocus() );
                }
            }
        });
        backButtonId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT){
                    backButtonId.fire();
                    Platform.runLater( () -> backButtonId.requestFocus() );
                }
            }
        });

    }

    @FXML
    void edit(ActionEvent event) {
         readerFromForm();
        try {
            dataHandler.edit(list, counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

