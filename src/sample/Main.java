package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.Questions;
import sample.helper.CheckHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/startPage.fxml"));
        primaryStage.setTitle("Burakov Bas UT Test Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(320);
        primaryStage.getScene().getStylesheets().add(Main.class.getResource("/sample/stylesheets/global.css").toExternalForm());
        //root.getStylesheets().add("sample/stylesheets/add-edit-remove.css");
        primaryStage.show();
    }

    public static ArrayList<Questions>list = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
        //ArrayList<Questions>list = new FileData().getQuestions();
        //takeTest(list);

    }

    /*public static void takeUITest(ArrayList<Questions>list){

    }*/
   /* public static void takeTest(ArrayList<Questions>list){
        int score = 0;
        //количество вопросов которые будут заданы
        int quantity;
        //считывание ответа с клавиатуру, сделано специально стрингом для проверки на число.
        String customNumber;
        //объект для считывания с клавитуры
        Scanner keyboardInput = new Scanner(System.in);
        //перемашать список вопросов
        Collections.shuffle(list);
        System.out.println("Введите количество вопросов от 1 до 300, иначе будут все вопросы 300 штук");
        customNumber = keyboardInput.nextLine();
        //если это число от 1 до 300 то назначается количество вопросов, иначе будут заданы все вопросы в тесте
        if(CheckHelper.isRealDigit(customNumber)){
            quantity = Integer.parseInt(customNumber);
        }else{
            quantity = list.size();
            System.out.println("\nТак как вы не ввели число в диапазоне от 1 до 300, то количество вопросов в тесте будет 300\n");
        }
        //перебор списка
        for(int i = 0; i < quantity; i++){
            //получение объекта Questions с вопросам, вариантами, и правильным ответом
            Questions question = list.get(i);
            System.out.println(question.getQuestion());
            //перебор массива String[] в Questions объекте и его распечатка
            int arrayQuestionAnswersLength = question.getAnswers().length;
            for(int j = 0;  j < arrayQuestionAnswersLength; j++){
                System.out.println(question.getAnswers()[j]);
            }
            //считывание ответа с клавиатуру
            String myAnswer;
            int myAnswerNumber;
            //проверка и принуждение пользователя к вводу простого числа от 1 до 300
            while(true){
                myAnswer = keyboardInput.nextLine();
                if(CheckHelper.isRealDigit(myAnswer)){
                    myAnswerNumber = Integer.parseInt(myAnswer);
                    //если ответ больше чем количество вопросов, пробовать ввести заново
                    if(myAnswerNumber > arrayQuestionAnswersLength || myAnswerNumber <= 0){
                        System.out.println("Введите пожалуйста целое число от 1 до " + arrayQuestionAnswersLength + " включительно");
                        continue;
                    }
                    break;
                }else{
                    System.out.println("Введите пожалуйста целое число от 1 до " + arrayQuestionAnswersLength + " включительно");
                }
            }

            //сравнение с результатом, если результат не верный вывод правильного ответа.
            if(myAnswerNumber == question.getResult()){
                score++;
                System.out.println("\nПоздравляем ваш ответ верный!\n");
            }else{
                System.out.println("\n!!! Ваш ответ не верный. Правильный ответ: " + question.getResult() + "\n");
            }
        }
        System.out.println("***\nВаш результат " + score + "/" + quantity);
    }*/

}
