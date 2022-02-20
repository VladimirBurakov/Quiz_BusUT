package sample.dao;

import sample.dao.model.Questions;
import sample.dao.model.User;
import sample.globalconstants.FileConst;
import sample.services.CurrentUserDataSaver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class FileDataHandler implements DataHandler {
    private boolean isTest = false;
    @Override
    public List<Questions> getAllData(){
        List<Path> pathList;
        List<Questions> list = new ArrayList<>();

        String question;
        String number;
        String[] answers;
        int result;

        try {
            pathList = Files.walk(Paths.get(FileConst.CSV_SAVE_DIRECTORY), 2).filter(x-> Files.isRegularFile(x)).collect(Collectors.toList());
            if(isTest){
                User currentUser = CurrentUserDataSaver.getCurrentUser();
                Collections.shuffle(pathList);
                pathList = pathList.stream().limit(currentUser.getQuestionsAmount()).collect(Collectors.toList());
            }else{
                pathList.sort(new Comparator<Path>() {
                    @Override
                    public int compare(Path o1, Path o2) {
                        String first = o1.getFileName().toString().substring(2).replaceAll(".csv", "");
                        String second = o2.getFileName().toString().substring(2).replaceAll(".csv", "");
                        return (int) Math.round(Double.parseDouble(first) - Double.parseDouble(second));
                    }
                });
            }

            for (Path path : pathList) {
                BufferedReader bfrd = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String data = bfrd.lines().collect(Collectors.joining());

                String[]innerArray = data.split("\\^");
                int innerArraySize = innerArray.length;
                question = innerArray[0].trim();
                number = question.substring(0, 4).trim();
                question = question.substring(4).trim();
                result = Integer.parseInt(innerArray[innerArraySize-1].trim());
                answers = Arrays.copyOfRange(innerArray, 1, innerArraySize - 1);
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
        String info;
        Questions question = list.get(counter);
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
        info = stringBuilder.toString();

        String fullStringPath = FileConst.CSV_SAVE_DIRECTORY + "/" + question.getNumber() + ".csv";
        Path fullPath = Paths.get(fullStringPath);
        Files.createFile(fullPath);
        byte[]tempInfo = info.getBytes(StandardCharsets.UTF_8);
        Files.write(fullPath, tempInfo, StandardOpenOption.WRITE);
    }

    @Override
    public void edit(List<Questions> list, int counter) throws IOException {
        String info;
        Questions question = list.get(counter);
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
        info = stringBuilder.toString();

        String fullStringPath = FileConst.CSV_SAVE_DIRECTORY + "/" + question.getNumber() + ".csv";
        Path fullPath = Paths.get(fullStringPath);
        byte[]tempInfo = info.getBytes(StandardCharsets.UTF_8);
        Files.write(fullPath, tempInfo, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public void remove(List<Questions> list, int counter) throws IOException {
        Questions question = list.get(counter);
        String fullStringPath = FileConst.CSV_SAVE_DIRECTORY + "/" + question.getNumber() + ".csv";
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
    }*/
}

