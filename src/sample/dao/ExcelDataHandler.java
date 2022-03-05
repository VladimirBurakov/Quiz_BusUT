package sample.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Main;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.dao.model.Questions;
import sample.dao.model.User;
import sample.globalconstants.FileConst;
import sample.services.CurrentUserDataSaver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ExcelDataHandler implements DataHandler {
    private boolean isTest = false;
    @Override
    public List<Questions> getAllData() {
        final int FIRST_COLUMN_INDEX = 0;
        final int SECOND_COLUMN_INDEX = 1;
        final int FIRST_ROW_INDEX = 0;
        final int SECOND_ROW_INDEX = 1;
        List<Path> pathList;
        List<Questions> list = new ArrayList<>();
        String question;
        String number;
        String [] answers = new String [6];
        int result;
        int id;
        int offset = 2;
        try {
            pathList = Files.walk(Paths.get(FileConst.XLSX_SAVE_DIRECTORY), 2).filter(x-> Files.isRegularFile(x)).collect(Collectors.toList());
            if(isTest){
                User currentUser = CurrentUserDataSaver.getCurrentUser();
                Collections.shuffle(pathList);
                pathList = pathList.stream().limit(currentUser.getQuestionsAmount()).collect(Collectors.toList());
            }else{
                pathList.sort(new Comparator<Path>() {
                    @Override
                    public int compare(Path o1, Path o2) {
                        String first = o1.getFileName().toString().substring(2).replaceAll(".xlsx", "");
                        String second = o2.getFileName().toString().substring(2).replaceAll(".xlsx", "");
                        return (int) Math.round(Double.parseDouble(first) - Double.parseDouble(second));
                    }
                });
            }

            for (Path path : pathList) {
                InputStream inputStream = new FileInputStream(path.toString());
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                Row idRow = sheet.getRow(FIRST_ROW_INDEX);
                Cell idCell = idRow.getCell(SECOND_COLUMN_INDEX);
                id = (int)idCell.getNumericCellValue();

                Row numberAndQuestionRow = sheet.getRow(SECOND_ROW_INDEX);
                Cell numberCell = numberAndQuestionRow.getCell(FIRST_COLUMN_INDEX);
                number = numberCell.getStringCellValue();
                Cell questionCell = numberAndQuestionRow.getCell(SECOND_COLUMN_INDEX);
                question = questionCell.getStringCellValue();

                for(int i = 0; i < 6; i++){
                    Row row = sheet.getRow(offset++);
                    Cell cell = row.getCell(SECOND_COLUMN_INDEX);
                    answers[i] = cell.getStringCellValue();
                }

                Row resultRow = sheet.getRow(offset);
                Cell resultCell = resultRow.getCell(SECOND_COLUMN_INDEX);
                result = (int)resultCell.getNumericCellValue();

                list.add(new Questions(question, answers, result, number, id));

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
        final int FIRST_COLUMN_INDEX = 0;
        final int SECOND_COLUMN_INDEX = 1;
        final int FIRST_ROW_INDEX = 0;
        final int SECOND_ROW_INDEX = 1;
        int OFFSET = 2;

        Questions question = list.get(counter);
        Workbook wb = new XSSFWorkbook();

        CellStyle style = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setDataFormat(format.getFormat("@"));
        style.setWrapText(true);

        Sheet sheet = wb.createSheet("TestData");

        FileOutputStream fos = new FileOutputStream(FileConst.XLSX_SAVE_DIRECTORY+ "/" + question.getNumber() + ".xlsx");

        Row idRow = sheet.createRow(FIRST_ROW_INDEX);
        Cell firstIdCell = idRow.createCell(FIRST_COLUMN_INDEX);
        Cell secondIdCell = idRow.createCell(SECOND_COLUMN_INDEX);
        firstIdCell.setCellStyle(style);
        secondIdCell.setCellStyle(style);
        firstIdCell.setCellValue("id");
        secondIdCell.setCellValue(question.getId());

        Row questionRow = sheet.createRow(SECOND_ROW_INDEX);
        Cell firstQuestionCell = questionRow.createCell(FIRST_COLUMN_INDEX);
        Cell secondQuestionCell = questionRow.createCell(SECOND_COLUMN_INDEX);
        firstQuestionCell.setCellStyle(style);
        secondQuestionCell.setCellStyle(style);
        firstQuestionCell.setCellValue(question.getNumber());
        secondQuestionCell.setCellValue(question.getQuestion());

        for(int i = 0; i < 6; i++){
            Row row = sheet.createRow(OFFSET++);
            Cell firstAnswerCell = row.createCell(FIRST_COLUMN_INDEX);
            firstAnswerCell.setCellValue("Вариант " + (i + 1));
            firstAnswerCell.setCellStyle(style);

            Cell secondAnswerCell = row.createCell(SECOND_COLUMN_INDEX);
            if(question.getAnswers()[i] != null){
                secondAnswerCell.setCellValue(question.getAnswers()[i]);
            }
            secondAnswerCell.setCellStyle(style);
        }

        Row resultRow = sheet.createRow(OFFSET);       //специально пропущена строка для отделения результата от теста
        Cell firstResultCell = resultRow.createCell(FIRST_COLUMN_INDEX);
        Cell secondResultCell = resultRow.createCell(SECOND_COLUMN_INDEX);
        firstResultCell.setCellStyle(style);
        secondResultCell.setCellStyle(style);
        firstResultCell.setCellValue("rightResult");
        secondResultCell.setCellValue(question.getResult());

        sheet.autoSizeColumn(FIRST_COLUMN_INDEX);
        sheet.autoSizeColumn(SECOND_COLUMN_INDEX);

        wb.write(fos);
    }

    @Override
    public void edit(List<Questions> list, int counter) throws IOException {
        add(list, counter);
    }

    @Override
    public void remove(List<Questions> list, int counter) throws IOException {
        Questions question = list.get(counter);
        String fullStringPath = FileConst.XLSX_SAVE_DIRECTORY + "/" + question.getNumber() + ".xlsx";
        Path fullPath = Paths.get(fullStringPath);
        Files.delete(fullPath);
    }

    @Override
    public void saveAllData(List<Questions> list, int counter) throws IOException {
        for(int i = 0; i < list.size(); i++){
            add(list, i);
        }
    }
}
