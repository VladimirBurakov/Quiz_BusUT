package sample.dao;

import java.util.ArrayList;
import java.util.Collections;

public class TestDataManager {
    //счет

    private static int score = 0;

    private static DataReceiver dataReceiver;

    private static ArrayList<Questions> list;
    static
    {
        dataReceiver = new JsonFileDataReceiver();
        list = dataReceiver.getQuestions();
    }

    //количество вопросов при запуске приложения выбор от 5 до 50
    private static int questionQuantity = 0;

    //Данные пользователя имя, фамилия, дата, кол-во вопросов, кол-во правильных ответов
    private static User currentUser;

    //получение текущего receiver
    public static DataReceiver getDataReceiver() {
        return dataReceiver;
    }
    public static int getScore() {
        return score;
    }
    public static int getQuestionQuantity() {
        return questionQuantity;
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    //выбор из чего получать, файл, json, MySql
   /* public static void setDataReceiver(DataReceiver dataReceiver) {
        TestDataManager.dataReceiver = dataReceiver;
    }*/
    public static void setQuestionQuantity(int questionQuantity) {
        TestDataManager.questionQuantity = questionQuantity;
    }
    public static void setScore(int score) {

        TestDataManager.score = score;
    }
    public static void setCurrentUser(User currentUser) {
        TestDataManager.currentUser = currentUser;
    }
    public static ArrayList<Questions> getList() {
        return list;
    }


    //возращение урезанного массива до questionQuantity
    public static ArrayList<Questions> getCurrentTestArray(){
        //перемашать список вопросов
        Collections.shuffle(list);
        ArrayList<Questions> currentList = new ArrayList<>();
        for(int i = 0; i < questionQuantity; i++){
            currentList.add(list.get(i));
        }
        return currentList;
    }
}
