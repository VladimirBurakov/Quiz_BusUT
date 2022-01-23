package sample.dao;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import sample.globalconstants.FileConst;

public class JsonFileDataReceiver implements DataReceiver{
    private String data;
    public JsonFileDataReceiver(){
        this.data = getData();
    }

    @Override
    public ArrayList<Questions> getQuestions(){
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
    };


    public String getData(){
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(FileConst.JSON_FILE)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

