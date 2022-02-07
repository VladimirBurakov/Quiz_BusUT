package sample.dao;

import sample.dao.model.Questions;

import java.util.ArrayList;
import java.util.List;

public interface DataHandler {
   List<Questions> getQuestions();
   void add(List<Questions> list, int counter);
   void edit(List<Questions> list, int counter);
   void remove(List<Questions> list, int counter);
}
