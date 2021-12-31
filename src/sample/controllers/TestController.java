package sample.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sample.dao.CurrentTestData;
import sample.dao.Questions;
import sample.helper.FXMLHelper;

public class TestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label intAllAmountAnswerLabel;

    @FXML
    private Button previousButton;

    @FXML
    private Label testTitleLabel;

    @FXML
    private RadioButton firstChoiceRadioButton;

    @FXML
    private RadioButton secondChoiceRadioButton;

    @FXML
    private RadioButton thirdChoiceRadioButton;
    @FXML
    private RadioButton fourthChoiceRadioButton;
    @FXML
    private RadioButton fifthChoiceRadioButton;
    @FXML
    private RadioButton sixthChoiceRadioButton;

    @FXML
    private Button approveButton;
    @FXML
    private Button nextButton;

    @FXML
    private ToggleGroup questionToggleGroup;

    @FXML
    private Label firstAnswerLabel;
    @FXML
    private Label secondAnswerLabel;
    @FXML
    private Label thirdAnswerLabel;
    @FXML
    private Label fourthAnswerLabel;
    @FXML
    private Label fifthAnswerLabel;
    @FXML
    private Label sixthAnswerLabel;

    @FXML
    private Label questionLabel;
    @FXML
    private Label intAmountAnswerLabel;
    @FXML
    private Label currentAnswerNumberLabel;

    private int counter = 0;
    private ArrayList<Questions> list;
    private RadioButton selection;

    @FXML
    void initialize() {
        //получение текущего массива с ограниченным числом вопросов
        list = new CurrentTestData().getCurrentTestArray();
        intAllAmountAnswerLabel.setText("" + CurrentTestData.getQuestionQuantity());
        intAmountAnswerLabel.setText(" "+ counter);
        Questions question = list.get(counter);
        takeTest(question);
        checkAnswer(question);
        nextQuestion();
    }

    private void takeTest(Questions question){
            //получение объекта Questions с вопросам, вариантами, и правильным ответом
            questionLabel.setText(question.getNumber() + " " + question.getQuestion());

            //перебор массива String[] в Questions объекте и его распечатка
            int arrayQuestionAnswersLength = question.getAnswers().length;
            for (int i = 0; i < arrayQuestionAnswersLength; i++) {
                switch (i){
                    case 0: firstAnswerLabel.setText(question.getAnswers()[i]);
                         break;
                    case 1: secondAnswerLabel.setText(question.getAnswers()[i]);
                        break;
                    case 2: thirdAnswerLabel.setText(question.getAnswers()[i]);
                        break;
                    case 3: fourthAnswerLabel.setText(question.getAnswers()[i]);
                        break;
                    case 4: fifthAnswerLabel.setText(question.getAnswers()[i]);
                        break;
                    case 5: sixthAnswerLabel.setText(question.getAnswers()[i]);
                        break;
                }
        }
    }

    private void checkAnswer(Questions question){
        //считывание ответа и вывод результата
        approveButton.setOnAction(event -> {
            selection = (RadioButton) questionToggleGroup.getSelectedToggle();
            if(selection != null && !selection.isDisabled()){
                int myAnswer = Integer.parseInt(selection.getText());
                //сравнение с результатом, если результат не верный вывод правильного ответа.
                if (myAnswer == question.getResult()) {
                    CurrentTestData.setScore(CurrentTestData.getScore() + 1);
                    currentAnswerNumberLabel.setText(String.valueOf("Верный ответ: " + question.getResult()) + "\nПоздравляем ваш ответ верный! :-)))\n");
                } else {
                    currentAnswerNumberLabel.setText("Вернный отвер: " + question.getResult() + "\nВаш ответ неверный! :-(((\n");
                }
                intAmountAnswerLabel.setText("" + (counter + 1));
                questionToggleGroup.getToggles().forEach(toggle -> {
                    Node node = (Node) toggle;
                    node.setDisable(true);
                });
            }
        });
    }

    private void nextQuestion(){
    //считывание ответа и вывод результата
        nextButton.setOnAction(event -> {
            if(selection != null){
                counter++;
                // Окончание и запуск результата !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if(counter >= CurrentTestData.getQuestionQuantity()){
                    nextButton.getScene().getWindow().hide();
                    FXMLHelper.loadPage("/sample/views/result.fxml");
                }else{
                    takeTest(list.get(counter));
                    checkAnswer(list.get(counter));
                    selection = null;
                    questionToggleGroup.getSelectedToggle().setSelected(false);
                    questionToggleGroup.getToggles().forEach(toggle -> {
                        Node node = (Node) toggle;
                        node.setDisable(false);
                    });
                    currentAnswerNumberLabel.setText("");
                }
            }
        });
    }
}
