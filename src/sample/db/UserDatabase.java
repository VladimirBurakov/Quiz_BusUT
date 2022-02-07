package sample.db;
import sample.dao.model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserDatabase extends AbstractDatabase {

    public void saveUser(User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registrationDate = simpleDateFormat.format(new Date());
        String insert = "INSERT INTO " + UserConst.USER_TABLE + "(" + UserConst.USER_FIRSTNAME + ", "
                    + UserConst.USER_LASTNAME + ", " + UserConst.USER_CURRENT_DATE + ", "
                    + UserConst.USER_QUESTIONS_AMOUHT + ", " + UserConst.USER_RIGHT_ANSWERS_AMOUNT + ")"
                    + "VALUES(?, ?, ?, ?, ?)";

        PreparedStatement prSt = null;
        try {
            prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, registrationDate);
            prSt.setInt(4, user.getQuestionsAmount());
            prSt.setInt(5, user.getRightAnswersAmount());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*public void saveResult(User user){
        String insert = "UPDATE " + UserConst.USER_TABLE + " SET " + UserConst.USER_RIGHT_ANSWERS_AMOUNT + " = ? WHERE " + UserConst.USER_ID +
                " = (SELECT MAX(" + UserConst.USER_ID +") FROM (SELECT * FROM " + UserConst.USER_TABLE + ") AS t)";

        PreparedStatement prSt = null;
        try {
            prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, user.getRightAnswersAmount());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    public ArrayList<User> getStat(){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT " + UserConst.USER_FIRSTNAME + ", " + UserConst.USER_LASTNAME + ", " + UserConst.USER_CURRENT_DATE + ", " +
                UserConst.USER_QUESTIONS_AMOUHT + ", " + UserConst.USER_RIGHT_ANSWERS_AMOUNT + " FROM " + UserConst.USER_TABLE +
                " ORDER BY " + UserConst.USER_CURRENT_DATE + " DESC LIMIT 10";
        Statement statement = null;
        try {
            statement = this.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString(UserConst.USER_FIRSTNAME);
                String lastName = resultSet.getString(UserConst.USER_LASTNAME);
                String dataTime = resultSet.getString(UserConst.USER_CURRENT_DATE);
                int amount = resultSet.getInt(UserConst.USER_QUESTIONS_AMOUHT);
                int answerAmount = resultSet.getInt(UserConst.USER_RIGHT_ANSWERS_AMOUNT);
                User user = new User(name, lastName, dataTime, amount, answerAmount);
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
