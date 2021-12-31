package sample.db;
import sample.dao.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler  extends Configs{

    private static Connection dbConnection;

    private Connection getDbConnection() throws SQLException, ClassNotFoundException {
        if(dbConnection == null){
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            return dbConnection;
        }else{
            return dbConnection;
        }
    }
    public void saveName(User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registrationDate = simpleDateFormat.format(new Date());
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIRSTNAME + ", "
                    + Const.USER_LASTNAME + ", " + Const.USER_CURRENT_DATE + ", "
                    + Const.USER_QUESTIONS_AMOUHT + ", " + Const.USER_RIGHT_ANSWERS_AMOUNT + ")"
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

    public void saveResult(User user){
        String insert = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_RIGHT_ANSWERS_AMOUNT + " = ? WHERE " + Const.USER_ID +
                " = (SELECT MAX(" + Const.USER_ID +") FROM (SELECT * FROM " + Const.USER_TABLE + ") AS t)";

        PreparedStatement prSt = null;
        try {
            prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, user.getRightAnswersAmount());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<User> getStat(){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT " + Const.USER_FIRSTNAME + ", " + Const.USER_LASTNAME + ", " + Const.USER_CURRENT_DATE + ", " +
                Const.USER_QUESTIONS_AMOUHT + ", " + Const.USER_RIGHT_ANSWERS_AMOUNT + " FROM " + Const.USER_TABLE +
                " ORDER BY " + Const.USER_CURRENT_DATE + " DESC LIMIT 10";
        Statement statement = null;
        try {
            statement = this.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString(Const.USER_FIRSTNAME);
                String lastName = resultSet.getString(Const.USER_LASTNAME);
                String dataTime = resultSet.getString(Const.USER_CURRENT_DATE);
                int amount = resultSet.getInt(Const.USER_QUESTIONS_AMOUHT);
                int answerAmount = resultSet.getInt(Const.USER_RIGHT_ANSWERS_AMOUNT);
                User user = new User(name, lastName, dataTime, amount, answerAmount);
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
