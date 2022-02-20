package sample.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.dao.model.Questions;
import sample.dao.model.User;
import sample.globalconstants.FileConst;
import sample.services.CurrentUserDataSaver;

public class JsonFileDataHandler implements DataHandler {
    private boolean isTest = false;
    @Override
    public List<Questions> getAllData() {
        List<Path> pathList;
        List<Questions> list = new ArrayList<>();
        String question;
        String number;
        int result;
        try {
            pathList = Files.walk(Paths.get(FileConst.JSON_SAVE_DIRECTORY), 2).filter(x-> Files.isRegularFile(x)).collect(Collectors.toList());
            if(isTest){
                User currentUser = CurrentUserDataSaver.getCurrentUser();
                Collections.shuffle(pathList);
                pathList = pathList.stream().limit(currentUser.getQuestionsAmount()).collect(Collectors.toList());
            }else{
                pathList.sort(new Comparator<Path>() {
                    @Override
                    public int compare(Path o1, Path o2) {
                        String first = o1.getFileName().toString().substring(2).replaceAll(".json", "");
                        String second = o2.getFileName().toString().substring(2).replaceAll(".json", "");
                        return (int) Math.round(Double.parseDouble(first) - Double.parseDouble(second));
                    }
                });
            }

            for (Path path : pathList) {
                /*BufferedReader bfrd = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                 String data = bfrd.lines().collect(Collectors.joining());
                 JSONObject jsonObject = new JSONObject(data);*/

                JSONObject jsonObject = new JSONObject(String.join("", Files.readAllLines(path, StandardCharsets.UTF_8)));
                int arrayLength = jsonObject.getJSONArray("answers").length();
                String[] answers = new String[arrayLength];
                question = jsonObject.getString("question");
                number = jsonObject.getString("number");
                result = jsonObject.getInt("result");
                JSONArray jsonArray = jsonObject.getJSONArray("answers");

                for (int j = 0; j < jsonArray.length(); j++) {
                    answers[j] = jsonArray.getString(j);
                }
                list.add(new Questions(question, answers, result, number));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<Questions> getOnlyTestData() {
        isTest = true;
        List<Questions> list = getAllData();
        isTest = false;
        return list;
    }

    @Override
    public void add(List<Questions> list, int counter) throws IOException {
        Questions question = list.get(counter);
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("number", question.getNumber());
        jsonItem.put("question", question.getQuestion());
        jsonItem.put("answers", question.getAnswers());
        jsonItem.put("result", question.getResult());

        String fullStringPath = FileConst.JSON_SAVE_DIRECTORY + "/" + question.getNumber() + ".json";
        Path fullPath = Paths.get(fullStringPath);
        Files.createFile(fullPath);
        byte[]tempInfo = jsonItem.toString(4).getBytes(StandardCharsets.UTF_8);
        Files.write(fullPath, tempInfo, StandardOpenOption.WRITE);
    }

    @Override
    public void edit(List<Questions> list, int counter) throws IOException {
        Questions question = list.get(counter);
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("number", question.getNumber());
        jsonItem.put("question", question.getQuestion());
        jsonItem.put("answers", question.getAnswers());
        jsonItem.put("result", question.getResult());

        String fullStringPath = FileConst.JSON_SAVE_DIRECTORY + "/" + question.getNumber() + ".json";
        Path fullPath = Paths.get(fullStringPath);
        byte[]tempInfo = jsonItem.toString(4).getBytes(StandardCharsets.UTF_8);
        Files.write(fullPath, tempInfo, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public void remove(List<Questions> list, int counter) throws IOException {
        Questions question = list.get(counter);
        String fullStringPath = FileConst.JSON_SAVE_DIRECTORY + "/" + question.getNumber() + ".json";
        Path fullPath = Paths.get(fullStringPath);
        Files.delete(fullPath);
    }

    @Override
    public void saveAllData(List<Questions> list, int counter) throws IOException {
        for(int i = 0; i < list.size(); i++){
            add(list, i);
        }
    }

    /*private String getData(){
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(FileConst.JSON_FILE)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }*/

    /*public void writeAll(List<Questions> list) {
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
    }*/
}

