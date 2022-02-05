package sample.dao;

import sample.db.TestsDatabaseHandler;

import java.util.ArrayList;

public class MySQLDataReceiver implements DataReceiver{
    private TestsDatabaseHandler testsDatabaseHandler;
    public MySQLDataReceiver(){
        testsDatabaseHandler = new TestsDatabaseHandler();
    }
    @Override
    public ArrayList<Questions> getQuestions() {
        return  testsDatabaseHandler.getDataBaseQuestions();
    }

}
