package sample.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class FileDataReceiver implements DataReceiver{
    private String data;
    public FileDataReceiver(){
        this.data = getData();
    }

    @Override
    public ArrayList<Questions> getQuestions(){
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
            question = question.substring(4);

            int result = Integer.parseInt(innerArray[innerArraySize-1].trim());
            answers = Arrays.copyOfRange(innerArray, 1, innerArraySize - 1);

            list.add(new Questions(question, answers, result, number));
        }
        return list;
    };

    @Override
     public String getData(){
        String result = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\java_projects\\Quiz\\src\\sample\\dao\\BAS_UT_Base.csv"), StandardCharsets.UTF_8))) {
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

