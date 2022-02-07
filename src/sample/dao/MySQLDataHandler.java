package sample.dao;

import sample.dao.model.Questions;
import sample.db.TestDatabase;
import java.util.List;

public class MySQLDataHandler implements DataHandler {
    private TestDatabase testDatabase = new TestDatabase();

    @Override
    public List<Questions> getQuestions() {
        return  testDatabase.getDataBaseQuestions();
    }

    @Override
    public void add(List<Questions> list, int counter) {
        testDatabase.addToMySQL(list.get(counter));
    }

    @Override
    public void edit(List<Questions> list, int counter) {
        testDatabase.editToMySQL(list.get(counter));
    }

    @Override
    public void remove(List<Questions> list, int counter) {
        testDatabase.removeToMySQL(list.get(counter));
    }
}
