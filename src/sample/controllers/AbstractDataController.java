package sample.controllers;

import javafx.scene.control.TextArea;
import sample.Main;
import sample.dao.*;
import sample.dao.model.Questions;

import java.util.List;

abstract class AbstractDataController {
    int counter = 0;
    List<Questions> list;
    TextArea[] textAreas;
    DataHandler dataHandler = Main.getDataForTest().getDataHandler();

    abstract void readerFromForm();

    abstract void setData(Questions question);

}
