package sample.db;

import sample.dao.model.Questions;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDatabase extends AbstractDatabase {

   //получение списка Question
    public List<Questions> getDataBaseQuestions(){
        ArrayList<Questions> resultList = new ArrayList<>();
        Statement statement = null;
        String commonQuery = "SELECT " + TestConst.QUIZ_TABLE + "." + TestConst.QUIZ_ID + ", " + TestConst.QUIZ_QNUMBER + ", " +
                TestConst.QUIZ_QUESTION + ", " + TestConst.QUIZ_RESULT + ", " +
                TestConst.ANSWERS_ANSWER + " FROM " + TestConst.QUIZ_TABLE + " RIGHT JOIN " + TestConst.ANSWERS_TABLE + " ON " +
                TestConst.QUIZ_TABLE + "." + TestConst.QUIZ_ID + " = " + TestConst.ANSWERS_TABLE + "." + TestConst.ANSWERS_QUIZID;
        try {
            statement = getDbConnection().createStatement();
            ResultSet commonResultSet = statement.executeQuery(commonQuery);
            ArrayList<String> tempArrayList = new ArrayList<>();
            int id = 0;
            String number = "";
            String simpleQuestion = "";
            String answer = "";
            int result = 0;
            Questions question;

            while (commonResultSet.next()) {
                int tempId = commonResultSet.getInt(TestConst.QUIZ_ID);
                if(id == 0){
                    id = tempId;
                    number = commonResultSet.getString(TestConst.QUIZ_QNUMBER);
                    simpleQuestion = commonResultSet.getString(TestConst.QUIZ_QUESTION);
                    result = commonResultSet.getInt(TestConst.QUIZ_RESULT);
                    answer = commonResultSet.getString(TestConst.ANSWERS_ANSWER);
                    tempArrayList.add(answer);
                }else if(id == tempId){
                    answer = commonResultSet.getString(TestConst.ANSWERS_ANSWER);
                    tempArrayList.add(answer);
                }else{
                    String[] answers = tempArrayList.toArray(new String[]{});
                    tempArrayList.clear();
                    question = new Questions(simpleQuestion, answers, result, number, id);
                    resultList.add(question);

                    id = tempId;
                    number = commonResultSet.getString(TestConst.QUIZ_QNUMBER);
                    simpleQuestion = commonResultSet.getString(TestConst.QUIZ_QUESTION);
                    result = commonResultSet.getInt(TestConst.QUIZ_RESULT);
                    answer = commonResultSet.getString(TestConst.ANSWERS_ANSWER);
                    tempArrayList.add(answer);
                }
            }
            String[] answers = tempArrayList.toArray(new String[]{});
            question = new Questions(simpleQuestion, answers, result, number, id);
            resultList.add(question);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void replaceAllToMySQL(List<Questions>questionsList) {
        for(Questions question: questionsList){
            addToMySQL(question);
        }
    }

    public void removeToMySQL(Questions question) {
        String removeQuery = "DELETE FROM " + TestConst.QUIZ_TABLE + " WHERE " + TestConst.QUIZ_ID + " = "
                + question.getId();
        Statement statement = null;
        try {
            statement = this.getDbConnection().createStatement();
            statement.execute(removeQuery);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editToMySQL(Questions question) {
        String editQuery = "UPDATE " + TestConst.QUIZ_TABLE + " SET " + TestConst.QUIZ_QNUMBER + " = ?, " + TestConst.QUIZ_QUESTION
                + " = ?, " + TestConst.QUIZ_RESULT + " = ? WHERE " + TestConst.QUIZ_ID + " = " + question.getId();

        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.getDbConnection().prepareStatement(editQuery);
            preparedStatement.setString(1, question.getNumber());
            preparedStatement.setString(2, question.getQuestion());
            preparedStatement.setInt(3, question.getResult());
            preparedStatement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        int currentId = getCurrentId(question.getId());
        String []answers = question.getAnswers();

        for(int i = 0; i < question.getAnswers().length; i++){
            editQuery  = "UPDATE " + TestConst.ANSWERS_TABLE + " SET " + TestConst.ANSWERS_ANSWER + " = ?"
                    + " WHERE " + TestConst.ANSWERS_QUIZID + " = ? AND "
                    + TestConst.ANSWER_ID + " = ?";
            try {
                preparedStatement = this.getDbConnection().prepareStatement(editQuery);
                preparedStatement.setString(1, answers[i]);
                preparedStatement.setInt(2, question.getId());
                preparedStatement.setInt(3, (currentId++));
                preparedStatement.executeUpdate();
            } catch (SQLException  | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToMySQL(Questions question) {

        String addQuery = "INSERT INTO " + TestConst.QUIZ_TABLE + "(" + TestConst.QUIZ_QNUMBER + ", "
                + TestConst.QUIZ_QUESTION + ", " + TestConst.QUIZ_RESULT + ")"
                + "VALUES(?, ?, ?)";

        PreparedStatement prSt = null;
        try {
            prSt = this.getDbConnection().prepareStatement(addQuery);
            prSt.setString(1, question.getNumber());
            prSt.setString(2, question.getQuestion());
            prSt.setInt(3, question.getResult());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        int maxId = getMaxId();
        String []answers = question.getAnswers();
        for(int i = 0; i < question.getAnswers().length; i++){
            addQuery = "INSERT INTO " + TestConst.ANSWERS_TABLE + "(" + TestConst.ANSWERS_QUIZID + ", "
                    + TestConst.ANSWERS_ANSWER + ")"
                    + "VALUES(?, ?)";
            try {
                prSt = this.getDbConnection().prepareStatement(addQuery);
                prSt.setInt(1, maxId);
                prSt.setString(2, answers[i]);
                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        question.setId(maxId);
    }

    private int getMaxId() {
        int id = 0;
        Statement statement = null;
        String query = "SELECT MAX(" + TestConst.QUIZ_ID + ") as maximum FROM " + TestConst.QUIZ_TABLE;
        try{
            statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("maximum");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }
    private int getCurrentId(int quizId) {
        int firstAnswerId = 0;
        Statement statement = null;
        String query = "SELECT " + TestConst.ANSWER_ID + " FROM " + TestConst.ANSWERS_TABLE + " WHERE "
                + TestConst.ANSWERS_QUIZID + " = " + quizId + " LIMIT 1";
        try{
            statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            firstAnswerId = resultSet.getInt(TestConst.ANSWER_ID);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return firstAnswerId;
    }
}
