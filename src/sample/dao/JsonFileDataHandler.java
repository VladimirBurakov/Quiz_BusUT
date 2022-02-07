package sample.dao;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.dao.model.Questions;
import sample.globalconstants.FileConst;

public class JsonFileDataHandler implements DataHandler {
    private String data;

    @Override
    public ArrayList<Questions> getQuestions(){
        this.data = getData();
        ArrayList<Questions> list = new ArrayList<>();
        String question;
        String number;
        int result;

        JSONArray jsonArray = new JSONArray(data);

        for(int i = 0; i < jsonArray.length(); i++){
            int arrayLength = jsonArray.getJSONObject(i).getJSONArray("answers").length();
            String [] answers = new String[arrayLength];
            question = jsonArray.getJSONObject(i).getString("question");
            number = jsonArray.getJSONObject(i).getString("number");
            result = jsonArray.getJSONObject(i).getInt("result");
            JSONArray innerJsonArray = jsonArray.getJSONObject(i).getJSONArray("answers");

            for(int j = 0; j < innerJsonArray.length(); j++){
                answers[j] = innerJsonArray.getString(j);
            }
            list.add(new Questions(question, answers, result, number));
        }
        return list;
    }

    @Override
    public void add(List<Questions> list, int counter) {
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

    @Override
    public void edit(List<Questions> list, int count) {
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

    @Override
    public void remove(List<Questions> list, int count) {
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

    private String getData(){
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(FileConst.JSON_FILE)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

