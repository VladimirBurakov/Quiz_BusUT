package sample.dao;

import java.util.ArrayList;
import java.util.Collections;

public class CurrentTestData {
    //счет
    private static int score = 0;

    //количество вопросов выбранные пользователем
    private static int questionQuantity = 0;

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentTestData.currentUser = currentUser;
    }

    private ArrayList<Questions> list = new FileDataReceiver().getQuestions();

    //возращение урезанного массива до questionQuantity
    public ArrayList<Questions> getCurrentTestArray(){
        //перемашать список вопросов
        Collections.shuffle(list);
        ArrayList<Questions> currentList = new ArrayList<>();
        for(int i = 0; i < questionQuantity; i++){
            currentList.add(list.get(i));
        }
        return currentList;
    }

    public static int getQuestionQuantity() {
        return questionQuantity;
    }

    public static void setQuestionQuantity(int questionQuantity) {
        CurrentTestData.questionQuantity = questionQuantity;
    }

    public static int getScore() {
        return score;
    }
    public static void setScore(int score) {

        CurrentTestData.score = score;
    }
}
