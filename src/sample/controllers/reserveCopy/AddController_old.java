package sample.controllers.reserveCopy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.services.FXMLHelper;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddController_old {

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
        setInitialValue();
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
                    FXMLHelper.setMessage("???????????? ???????? ?????????? ??????????????\n ??.?? ??.???? ?????? ??.??????\n???????????? ?????????? ???? 1 ???? 4 ????????????????????????!");
                    FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
                }
            }
        });

        questionTextAreaId.setOnKeyTyped(event -> {
            firstTextAreaAnswerId.setDisable(false);
            turnOnAddButton();
        });

        firstTextAreaAnswerId.setOnKeyTyped(event -> {
            secondTextAreaAnswerId.setDisable(false);
            turnOnAddButton();
        });

        secondTextAreaAnswerId.setOnKeyTyped(event -> {
            thirdTextAreaAnswerId.setDisable(false);
            turnOnAddButton();
        });

        thirdTextAreaAnswerId.setOnKeyTyped(event -> {
            fourthTextAreaAnswerId.setDisable(false);
            rightAnswerTextFieldId.setDisable(false);
            turnOnAddButton();
        });

        fourthTextAreaAnswerId.setOnKeyTyped(event -> {
            fifthTextAreaAnswerId.setDisable(false);
        });
        fifthTextAreaAnswerId.setOnKeyTyped(event -> {
            sixthTextAreaAnswerId.setDisable(false);
        });
        rightAnswerTextFieldId.setOnAction(keyEvent -> {
            turnOnAddButton();
        });

    }

    private boolean checkRightAnswerTextField() {
        String rightAnswerString;
        if (!rightAnswerTextFieldId.getText().isEmpty()) {
            rightAnswerString = rightAnswerTextFieldId.getText();
            if (Pattern.matches("[1-6]", rightAnswerString)) {
                rightAnswerTextFieldId.setStyle("-fx-background-color: #AAFFAA");
                return true;
            }
            else{
                rightAnswerTextFieldId.setStyle("-fx-background-color: #FF1111");
                FXMLHelper.setMessage("?????????? ???????????? ????????\n?????????? ???? 1 \n???? 6 ????????????????????????!");
                FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
            }

        }
        return false;
    }

    private void turnOnAddButton(){
        if(checkRightAnswerTextField() && !questionTextAreaId.getText().isEmpty() && !firstTextAreaAnswerId.getText().isEmpty() && !secondTextAreaAnswerId.getText().isEmpty() && !thirdTextAreaAnswerId.getText().isEmpty()){
            addButtonId.setDisable(false);
        }else{
            addButtonId.setDisable(true);
        }
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


    @FXML
    void exitSettings(ActionEvent event) {
        Stage stage = (Stage) exitButtonId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event){
        /*String result = readerFromForm();
        writeToFile(result);
        setInitialValue();*/
    }

     /*private void exitSettings(){
        exitButtonId.setOnAction(event -> {
            Stage stage = (Stage) exitButtonId.getScene().getWindow();
            stage.close();
        });
    }*/
}