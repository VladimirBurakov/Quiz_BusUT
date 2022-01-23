package sample.db;

import sample.dao.Questions;
import java.sql.*;
import java.util.ArrayList;

public class TestInfoDatabaseHandler extends AbstractDataBaseHandler{

 /*   public void saveName(User user){
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
    }*/
   //получение списка Question
    public ArrayList<Questions> getDataBaseQuestions(){
        ArrayList<Questions> resultList = new ArrayList<>();
        Statement statement = null;
        String commonQuery = "SELECT " + TestInfoConst.TEST_TABLE + "." + TestInfoConst.TEST_ID + ", " + TestInfoConst.TEST_NUMBER + ", " +
                TestInfoConst.TEST_QUESTION + ", " + TestInfoConst.TEST_RESULT + ", " +
                TestInfoConst.TEST_ANSWERS + " FROM " + TestInfoConst.TEST_TABLE + " RIGHT JOIN " + TestInfoConst.TEST_TABLE_ANSWERS + " ON " +
                TestInfoConst.TEST_TABLE + "." + TestInfoConst.TEST_ID + " = " + TestInfoConst.TEST_TABLE_ANSWERS + "." + TestInfoConst.TEST_ANSWERS_ID;
        try {
            statement = getDbConnection().createStatement();
            ResultSet commonResultSet = statement.executeQuery(commonQuery);
            ArrayList<String> tempArrayList = new ArrayList<>();
            String id = "";
            String number = "";
            String simpleQuestion = "";
            String answer = "";
            int result = 0;
            Questions question;

            while (commonResultSet.next()) {
                String tempId = commonResultSet.getString(TestInfoConst.TEST_ID);
                if(id.equals("")){
                    id = tempId;
                    number = commonResultSet.getString(TestInfoConst.TEST_NUMBER);
                    simpleQuestion = commonResultSet.getString(TestInfoConst.TEST_QUESTION);
                    result = commonResultSet.getInt(TestInfoConst.TEST_RESULT);
                    answer = commonResultSet.getString(TestInfoConst.TEST_ANSWERS);
                    tempArrayList.add(answer);
                }else if(id.equals(tempId)){
                    answer = commonResultSet.getString(TestInfoConst.TEST_ANSWERS);
                    tempArrayList.add(answer);
                }else{
                    String[] answers = tempArrayList.toArray(new String[]{});
                    tempArrayList.clear();
                    question = new Questions(simpleQuestion, answers, result, number);
                    resultList.add(question);

                    id = tempId;
                    number = commonResultSet.getString(TestInfoConst.TEST_NUMBER);
                    simpleQuestion = commonResultSet.getString(TestInfoConst.TEST_QUESTION);
                    result = commonResultSet.getInt(TestInfoConst.TEST_RESULT);
                    answer = commonResultSet.getString(TestInfoConst.TEST_ANSWERS);
                    tempArrayList.add(answer);
                }
            }
            String[] answers = tempArrayList.toArray(new String[]{});
            question = new Questions(simpleQuestion, answers, result, number);
            resultList.add(question);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
