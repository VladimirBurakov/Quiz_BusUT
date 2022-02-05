package sample.services;

import sample.dao.DataReceiver;
import sample.dao.MySQLDataReceiver;
import sample.dao.Questions;
import sample.dao.User;

import java.util.ArrayList;
import java.util.Collections;

public class CurrentUserDataSaver {

    private static DataReceiver dataReceiver;

    private static ArrayList<Questions> list;

    //chose receiver
    //выбор из чего получать, файл, json, MySql                  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    static
    {
        //dataReceiver = new JsonFileDataReceiver();
        dataReceiver = new MySQLDataReceiver();
        list = dataReceiver.getQuestions();
    }

    //Данные пользователя имя, фамилия, дата, кол-во вопросов, кол-во правильных ответов
    private static User currentUser;

    //получение текущего receiver
    public static DataReceiver getDataReceiver() {
        return dataReceiver;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUserDataSaver.currentUser = currentUser;
    }
    public static ArrayList<Questions> getList() {
        return list;
    }

    //возращение урезанного массива до questionQuantity
    public static ArrayList<Questions> getCurrentTestArray(){
        //перемашать список вопросов
        Collections.shuffle(list);
        ArrayList<Questions> currentList = new ArrayList<>();
        for(int i = 0; i < currentUser.getQuestionsAmount(); i++){
            currentList.add(list.get(i));
        }
        return currentList;
    }
}
