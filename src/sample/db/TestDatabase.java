package sample.db;

import sample.dao.model.Questions;
import sample.services.CurrentUserDataSaver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDatabase extends AbstractDatabase {
    private String forTest = "";

   //получение списка Question
    public List<Questions> getAllDataFromMySQL(){
        List<Questions> resultList = new ArrayList<>();
        Statement statement = null;
        String quizQuery = "SELECT " + TestConst.QUIZ_TABLE + "." + TestConst.QUIZ_ID + ", " + TestConst.QUIZ_QNUMBER + ", " +
                TestConst.QUIZ_QUESTION + ", " + TestConst.QUIZ_RESULT + " FROM " + TestConst.QUIZ_TABLE + forTest;

        try {
            statement = getDbConnection().createStatement();
            ResultSet quizResultSet = statement.executeQuery(quizQuery);
            int id;
            String number;
            String simpleQuestion;
            int result;
            Questions question;

            while (quizResultSet.next()) {
                id = quizResultSet.getInt(TestConst.QUIZ_ID);
                number = quizResultSet.getString(TestConst.QUIZ_QNUMBER);
                simpleQuestion = quizResultSet.getString(TestConst.QUIZ_QUESTION);
                result = quizResultSet.getInt(TestConst.QUIZ_RESULT);

                question = new Questions(simpleQuestion, new String[0], result, number, id);
                resultList.add(question);
            }

            int tempId;
            String answerQuery;
            List<String> tempArrayList = new ArrayList<>();

            for(int i = 0; i < resultList.size(); i++){
                tempId = resultList.get(i).getId();
                answerQuery = "SELECT " + TestConst.ANSWERS_QUIZ_ID + ", " + TestConst.ANSWERS_ANSWER +
                        " FROM " + TestConst.ANSWERS_TABLE + " WHERE " + TestConst.ANSWERS_QUIZ_ID + " = " + tempId;
                ResultSet answerResultSet = statement.executeQuery(answerQuery);
                while(answerResultSet.next()){
                    tempArrayList.add(answerResultSet.getString(TestConst.ANSWERS_ANSWER));
                }
                String[] answers = tempArrayList.toArray(new String[]{});
                resultList.get(i).setAnswers(answers);
                tempArrayList.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /*public List<Questions> getAllDataFromMySQL(){                 //старый вариант не работает с функцией rand()
        List<Questions> resultList = new ArrayList<>();
        Statement statement = null;
        String commonQuery = "SELECT " + TestConst.QUIZ_TABLE + "." + TestConst.QUIZ_ID + ", " + TestConst.QUIZ_QNUMBER + ", " +
                TestConst.QUIZ_QUESTION + ", " + TestConst.QUIZ_RESULT + ", " +
                TestConst.ANSWERS_ANSWER + " FROM " + TestConst.QUIZ_TABLE + " RIGHT JOIN " + TestConst.ANSWERS_TABLE + " ON " +
                TestConst.QUIZ_TABLE + "." + TestConst.QUIZ_ID + " = " + TestConst.ANSWERS_TABLE + "." + TestConst.ANSWERS_QUIZID + forTest;
        try {
            statement = getDbConnection().createStatement();
            ResultSet commonResultSet = statement.executeQuery(commonQuery);
            List<String> tempArrayList = new ArrayList<>();
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
    }*/

    public List<Questions> getOnlyTestDataFromMySQL(){
        int questionsAmount = CurrentUserDataSaver.getCurrentUser().getQuestionsAmount();
        forTest = " ORDER BY rand() LIMIT " + questionsAmount;
        List<Questions> result = getAllDataFromMySQL();
        forTest = "";
        return result;
    }

    public void saveAllDataToMySQL(List<Questions>questionsList) {
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
        //int currentId = getCurrentId(question.getId());
        String []answers = question.getAnswers();

        for(int i = 0; i < question.getAnswers().length; i++){
            editQuery  = "UPDATE " + TestConst.ANSWERS_TABLE + " SET " + TestConst.ANSWERS_ANSWER + " = ?"
                    + " WHERE " + TestConst.ANSWERS_QUIZ_ID + " = ? AND "
                    + TestConst.ANSWER_ARRAY_INDEX + " = ?";
            try {
                preparedStatement = this.getDbConnection().prepareStatement(editQuery);
                preparedStatement.setString(1, answers[i]);
                preparedStatement.setInt(2, question.getId());
                preparedStatement.setInt(3, i);
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
        question.setId(maxId);
        String []answers = question.getAnswers();
        for(int i = 0; i < question.getAnswers().length; i++){
            addQuery = "INSERT INTO " + TestConst.ANSWERS_TABLE + "(" + TestConst.ANSWERS_QUIZ_ID + ", "
                    + TestConst.ANSWERS_ANSWER + ", "+ TestConst.ANSWER_ARRAY_INDEX + ")"
                    + "VALUES(?, ?, " + i +")";
            try {
                prSt = this.getDbConnection().prepareStatement(addQuery);
                prSt.setInt(1, maxId);
                prSt.setString(2, answers[i]);
                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
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

    /*private int getCurrentId(int quizId) {
        int firstAnswerId = 0;
        Statement statement = null;
        String query = "SELECT " + TestConst.ANSWER_ID + " FROM " + TestConst.ANSWERS_TABLE + " WHERE "
                + TestConst.ANSWERS_QUIZ_ID + " = " + quizId + " LIMIT 1";
        try{
            statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            firstAnswerId = resultSet.getInt(TestConst.ANSWER_ID);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return firstAnswerId;
    }*/
}
