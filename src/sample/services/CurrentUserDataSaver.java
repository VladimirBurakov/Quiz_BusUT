package sample.services;

import sample.dao.model.User;
import sample.db.UserDatabase;

public class CurrentUserDataSaver {

    //Данные пользователя имя, фамилия, дата, кол-во вопросов, кол-во правильных ответов
    private static User currentUser;

    //получение текущего receiver
    public static User getCurrentUser() {
        return currentUser;
    }


    public static void setCurrentUser(User currentUser) {
        CurrentUserDataSaver.currentUser = currentUser;
    }

    public static void saveCurrentUserToDataBase(){
        UserDatabase dbHandler = new UserDatabase();
        dbHandler.saveUser(currentUser);
    }
}
