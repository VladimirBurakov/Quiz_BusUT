package sample.controllers;

import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.dao.*;
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
    void saveTo(){
        if(TestDataManager.getDataReceiver() instanceof MySQLDataReceiver){
            //реализовать
             //saveToMySQL();
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
