package sample.dao;

import sample.dao.model.Questions;
import sample.globalconstants.FileConst;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDataHandler implements DataHandler {

    @Override
    public ArrayList<Questions> getQuestions(){
        String data = getData();
        String[] array = data.split("\\*");
        String[] answers;
        String question;
        String number;

        ArrayList<Questions>list = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            String[]innerArray = array[i].split("\\^");
            int innerArraySize = innerArray.length;
            question = innerArray[0].trim();
            number = question.substring(0, 4).trim();
            question = question.substring(4).trim();

            int result = Integer.parseInt(innerArray[innerArraySize-1].trim());
            answers = Arrays.copyOfRange(innerArray, 1, innerArraySize - 1);

            list.add(new Questions(question, answers, result, number));
        }
        return list;
    }

    @Override
    public void add(List<Questions> list, int counter) {
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

    @Override
    public void edit(List<Questions> list, int counter) {
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

    @Override
    public void remove(List<Questions> list, int counter) {
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

     private String getData(){
        String result = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileConst.CSV_FILE), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

