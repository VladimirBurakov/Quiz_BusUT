package sample.dao;

import sample.dao.model.Questions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface DataHandler {
   List<Questions> getAllData();
   List<Questions> getOnlyTestData();
   void add(List<Questions> list, int counter) throws IOException;
   void edit(List<Questions> list, int counter) throws IOException;
   void remove(List<Questions> list, int counter) throws IOException;
   void saveAllData(List<Questions> list, int counter) throws IOException;

}
