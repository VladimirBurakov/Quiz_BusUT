package sample.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.helper.FXMLHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButtonId;

    @FXML
    private TextArea fourthTextAreaAnswerId;

    @FXML
    private TextField questionNumberTextFieldId;

    @FXML
    private TextArea firstTextAreaAnswerId;

    @FXML
    private TextArea thirdTextAreaAnswerId;

    @FXML
    private TextArea fifthTextAreaAnswerId;

    @FXML
    private TextArea secondTextAreaAnswerId;

    @FXML
    private TextArea sixthTextAreaAnswerId;

    @FXML
    private Button addButtonId;

    @FXML
    private TextField rightAnswerTextFieldId;

    @FXML
    private TextArea questionTextAreaId;

    @FXML
    void initialize() {
        /*setInitialValue();
        addButtonId.setOnAction(event -> {
            String result = readerFromForm();
            writeToFile(result);
        });
        exitSettings();*/
        fieldsBlockageController();
    }
    private void setInitialValue(){
        questionNumberTextFieldId.setDisable(false);
        questionTextAreaId.setDisable(true);
        firstTextAreaAnswerId.setDisable(true);
        secondTextAreaAnswerId.setDisable(true);
        thirdTextAreaAnswerId.setDisable(true);
        fourthTextAreaAnswerId.setDisable(true);
        fifthTextAreaAnswerId.setDisable(true);
        sixthTextAreaAnswerId.setDisable(true);
        rightAnswerTextFieldId.setDisable(false);
        addButtonId.setDisable(true);

        questionNumberTextFieldId.setStyle("");
        rightAnswerTextFieldId.setStyle("");

        questionNumberTextFieldId.setText("");
        questionTextAreaId.setText("");
        firstTextAreaAnswerId.setText("");
        secondTextAreaAnswerId.setText("");
        thirdTextAreaAnswerId.setText("");
        fourthTextAreaAnswerId.setText("");
        fifthTextAreaAnswerId.setText("");
        sixthTextAreaAnswerId.setText("");
        rightAnswerTextFieldId.setText("");
    }

    private void fieldsBlockageController(){

        questionNumberTextFieldId.setOnKeyPressed(event -> {
            String rightAnswerString;
            if(event.getCode() == KeyCode.ENTER){
                rightAnswerString = questionNumberTextFieldId.getText();
                if(Pattern.matches("[1-4]\\.\\d{1,3}", rightAnswerString)){
                    questionNumberTextFieldId.setStyle("-fx-background-color: #AAFFAA");
                    questionTextAreaId.setDisable(false);
                    questionNumberTextFieldId.setDisable(true);
                }else{
                    questionNumberTextFieldId.setStyle("-fx-background-color: #FF1111");
                    FXMLHelper.setMessage("Должно быть число формата\n х.х х.хх или х.ххх\nпервое число от 1 до 4 включительно!");
                    FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
                }
            }
        });

        questionTextAreaId.setOnKeyTyped(event -> {
            firstTextAreaAnswerId.setDisable(false);
        });

        firstTextAreaAnswerId.setOnKeyTyped(event -> {
            secondTextAreaAnswerId.setDisable(false);
        });

        secondTextAreaAnswerId.setOnKeyTyped(event -> {
            thirdTextAreaAnswerId.setDisable(false);
        });

        thirdTextAreaAnswerId.setOnKeyTyped(event -> {
            fourthTextAreaAnswerId.setDisable(false);
            rightAnswerTextFieldId.setDisable(false);
        });

        fourthTextAreaAnswerId.setOnKeyTyped(event -> {
            fifthTextAreaAnswerId.setDisable(false);
        });
        fifthTextAreaAnswerId.setOnKeyTyped(event -> {
            sixthTextAreaAnswerId.setDisable(false);
        });
        rightAnswerTextFieldId.setOnKeyPressed(keyEvent -> {
            String rightAnswerString;
            if(keyEvent.getCode() == KeyCode.ENTER){
                rightAnswerString = rightAnswerTextFieldId.getText();
                if(Pattern.matches("[1-6]", rightAnswerString)){
                    rightAnswerTextFieldId.setStyle("-fx-background-color: #AAFFAA");
                    addButtonId.setDisable(false);
                }else{
                    rightAnswerTextFieldId.setStyle("-fx-background-color: #FF1111");
                    FXMLHelper.setMessage("Ответ должна быть\nцифра от 1 \nдо 6 включительно!");
                    FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
                }
            }
        });

    }

    private String readerFromForm(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(questionNumberTextFieldId.getText());
        stringBuilder.append(" ");
        stringBuilder.append(questionTextAreaId.getText());
        stringBuilder.append("\n^");
        if(!firstTextAreaAnswerId.getText().isEmpty()){
            stringBuilder.append("\n\t1. ");
            stringBuilder.append(firstTextAreaAnswerId.getText());
            stringBuilder.append("\n^");
            if(!secondTextAreaAnswerId.getText().isEmpty()){
                stringBuilder.append("\n\t2. ");
                stringBuilder.append(secondTextAreaAnswerId.getText());
                stringBuilder.append("\n^");
                }
                if(!thirdTextAreaAnswerId.getText().isEmpty()){
                    stringBuilder.append("\n\t3. ");
                    stringBuilder.append(thirdTextAreaAnswerId.getText());
                    stringBuilder.append("\n^");
                    if(!fourthTextAreaAnswerId.getText().isEmpty()){
                        stringBuilder.append("\n\t4. ");
                        stringBuilder.append(fourthTextAreaAnswerId.getText());
                        stringBuilder.append("\n^");
                        if(!fifthTextAreaAnswerId.getText().isEmpty()){
                            stringBuilder.append("\n\t5. ");
                            stringBuilder.append(fifthTextAreaAnswerId.getText());
                            stringBuilder.append("\n^");
                            if(!sixthTextAreaAnswerId.getText().isEmpty()){
                                stringBuilder.append("\n\t6. ");
                                stringBuilder.append(sixthTextAreaAnswerId.getText());
                                stringBuilder.append("\n^");
                            }
                        }
                    }
                }
            }
            stringBuilder.append("\n");
            stringBuilder.append(rightAnswerTextFieldId.getText());
            stringBuilder.append("\n*\n");

        return stringBuilder.toString();
    }

    private void writeToFile(String question){

        String file = "D:\\java_projects\\Quiz\\src\\sample\\dao\\BAS_UT_Base.csv";

        try {
            Files.write(Paths.get(file), question.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setInitialValue();
    }

    @FXML
    void exitSettings(ActionEvent event) {
        Stage stage = (Stage) exitButtonId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void writeInfo(ActionEvent event){
        String result = readerFromForm();
        writeToFile(result);
        setInitialValue();
    }

     /*private void exitSettings(){
        exitButtonId.setOnAction(event -> {
            Stage stage = (Stage) exitButtonId.getScene().getWindow();
            stage.close();
        });
    }*/
}