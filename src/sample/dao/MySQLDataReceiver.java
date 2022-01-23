package sample.dao;

import sample.db.TestInfoDatabaseHandler;

import java.util.ArrayList;

public class MySQLDataReceiver implements DataReceiver{
    private TestInfoDatabaseHandler testInfoDatabaseHandler;
    MySQLDataReceiver(){
        testInfoDatabaseHandler = new TestInfoDatabaseHandler();
    }
    @Override
    public ArrayList<Questions> getQuestions() {
        return  testInfoDatabaseHandler.getDataBaseQuestions();
    }

}
