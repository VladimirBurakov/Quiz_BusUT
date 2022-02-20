package sample.dao;

import sample.dao.model.Questions;

import java.io.IOException;
import java.util.List;

public class ExcelDataHandler implements DataHandler {
    @Override
    public List<Questions> getAllData() {
        return null;
    }

    @Override
    public List<Questions> getOnlyTestData() {
        return null;
    }

    @Override
    public void add(List<Questions> list, int counter) throws IOException {

    }

    @Override
    public void edit(List<Questions> list, int counter) throws IOException {

    }

    @Override
    public void remove(List<Questions> list, int counter) throws IOException {

    }

    @Override
    public void saveAllData(List<Questions> list, int counter) throws IOException {

    }
}
