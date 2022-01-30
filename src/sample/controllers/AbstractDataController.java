package sample.controllers;

import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.dao.*;
import sample.db.TestsDatabaseHandler;
import sample.globalconstants.FileConst;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataController {
    int counter = 0;
    ArrayList<Questions> list;
    TextArea[] textAreas;
    private TestsDatabaseHandler testsDatabaseHandler = new TestsDatabaseHandler();

    private void saveToJSONFile() {
        readerFromForm();
        JSONArray jsonArray = new JSONArray();
        for(Questions question: list){
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("number", question.getNumber());
            jsonItem.put("question", question.getQuestion());
            jsonItem.put("answers", question.getAnswers());
            jsonItem.put("result", question.getResult());
            jsonArray.put(jsonItem);
        }
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(FileConst.JSON_FILE), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            bufferedWriter.write(jsonArray.toString(4));
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(){
        readerFromForm();
        List<String> stringList = new ArrayList<>();
        String info;
        for(Questions question: list){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(question.getNumber());
            stringBuilder.append(" ");
            stringBuilder.append(question.getQuestion());
            stringBuilder.append("\n^");
            for (int i = 0; i < question.getAnswers().length; i++) {
                String[] array = question.getAnswers();
                if (!array[i].isEmpty()) {
                    stringBuilder.append("\n");
                    stringBuilder.append(array[i]);
                    stringBuilder.append("\n^");
                }
            }

            stringBuilder.append("\n");
            stringBuilder.append(question.getResult());
            stringBuilder.append("\n*\n");

            info = stringBuilder.toString();
            stringList.add(info);
        }
        try {
            Files.write(Paths.get(FileConst.CSV_FILE), stringList, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToMySQL(SaveType type) {
        if(type == SaveType.ADD){
            readerFromForm();
            testsDatabaseHandler.addToMySQL(list.get(counter));
        }else if(type == SaveType.EDIT){
            readerFromForm();
            testsDatabaseHandler.editToMySQL(list.get(counter));
        }else if(type == SaveType.REMOVE){
            testsDatabaseHandler.removeToMySQL(list.get(counter));
            readerFromForm();
        }else if(type == SaveType.REPLACE_ALL){
            testsDatabaseHandler.replaceAllToMySQL(list);
        }
    }

    //для тестирования и перегонки данных в нужный файл, после заменить на TestDataManager.getDataReceiver().
    void saveTo(SaveType type){
        if(TestDataManager.getDataReceiver() instanceof MySQLDataReceiver){
            saveToMySQL(type);
            System.out.println("");
        }else if(TestDataManager.getDataReceiver() instanceof JsonFileDataReceiver){
            saveToJSONFile();
        }else{
            saveToFile();
            //saveToJSONFile();
        }
    }

    abstract void readerFromForm();

    abstract void setData(Questions question);

}
