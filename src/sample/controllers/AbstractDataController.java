package sample.controllers;

import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.dao.JsonFileDataReceiver;
import sample.dao.MySQLDataReceiver;
import sample.dao.Questions;
import sample.dao.TestDataManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractDataController {
    int counter = 0;
    ArrayList<Questions> list;
    TextArea[] textAreas;
    private final String JSON_FILE = "D:\\java_projects\\Quiz\\src\\sample\\dao\\JSON_BAS_UT_Base.json";
    private final String CSV_FILE = "D:\\java_projects\\Quiz\\src\\sample\\dao\\BAS_UT_Base.json";

    private void saveToJSONFile() {
        readerFromForm();
        JSONArray jsonArray = new JSONArray();
        for(Questions question: list){
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("number", question.getNumber());
            jsonItem.put("question", question.getQuestion());
            JSONArray innerJsonArray = new JSONArray();
            for (int i = 0; i < question.getAnswers().length; i++) {
                String[] array = question.getAnswers();
                if (!array[i].isEmpty()) {
                    innerJsonArray.put(array[i]);
                }
            }
            if(innerJsonArray.length() < 6){
                for(int i = innerJsonArray.length(); i < 6; i++){
                    innerJsonArray.put("");
                }
            }
            jsonItem.put("answers", innerJsonArray);
            jsonItem.put("result", question.getResult());
            jsonArray.put(jsonItem);
        }
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(JSON_FILE), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
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
            Files.write(Paths.get(CSV_FILE), stringList, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void saveTo(){
        if(TestDataManager.getDataReceiver() instanceof MySQLDataReceiver){
            //реализовать
            // saveToMySQL();
            System.out.println("");
        }else if(TestDataManager.getDataReceiver() instanceof JsonFileDataReceiver){
            saveToJSONFile();
        }else{
            saveToFile();
        }
    }

    abstract void readerFromForm();

    abstract void setData(Questions question);

}
