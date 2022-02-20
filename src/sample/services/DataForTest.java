package sample.services;

import sample.dao.DataHandler;
import sample.dao.model.Questions;
import sample.dao.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataForTest {
    private DataHandler dataHandler;
    //private List<Questions> list;

    public DataForTest(DataHandler dataHandler){
        this.dataHandler = dataHandler;
        //list = dataHandler.getAllData();
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
    public void setDataHandler(DataHandler dataHandler) {
       this.dataHandler = dataHandler;
    }

    /*public List<Questions> getList() {
        return list;
    }*/

    // после раализации метода ДатаХэндлер этот метод убрать.
    /*public List<Questions> getCurrentTestArray(){
        User currentUser = CurrentUserDataSaver.getCurrentUser();
        Collections.shuffle(list);
        List<Questions> currentList = new ArrayList<>();
        for(int i = 0; i < currentUser.getQuestionsAmount(); i++){
            currentList.add(list.get(i));
        }
        return currentList;
    }*/
}
