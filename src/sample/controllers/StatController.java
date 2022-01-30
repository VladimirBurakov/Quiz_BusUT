package sample.controllers;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.dao.User;
import sample.db.UsersDatabaseHandler;

public class StatController {

    @FXML
    private Label label;

    @FXML
    private ResourceBundle resources;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String>dataTimeColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, Integer> answerAmountColumn;

    @FXML
    private TableColumn<User, Integer> questionAmountColumn;

    @FXML
    private TableView<User> tableViewTable;

    @FXML
    void initialize() {
        this.setQuery();
    }

    @FXML
    void exitStat(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE){
            Scene scene = ((Node)event.getSource()).getScene();
            scene.getWindow().hide();
        }
    }

    private void setQuery(){
        String centerStyle = "-fx-alignment: CENTER;";

        UsersDatabaseHandler dbHandler = new UsersDatabaseHandler();
        ArrayList<User> users = dbHandler.getStat();
        ObservableList<User> observableList = FXCollections.observableList(users);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        dataTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dataTime"));
        dataTimeColumn.setStyle(centerStyle);

        questionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("questionsAmount"));
        questionAmountColumn.setStyle(centerStyle);

        answerAmountColumn.setCellValueFactory(new PropertyValueFactory<>("rightAnswersAmount"));
        answerAmountColumn.setStyle(centerStyle);

        tableViewTable.setItems(observableList);
    }

    // рефлексия и даункаст

    /*private void setQuery(){
        DatabaseHandler dbHandler = new DatabaseHandler();
        ArrayList<User> users = dbHandler.getStat();
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Field field1;
            Field field2;
            Field field3;
            Field field4;
            Field field5;
            int j = i + 1;
            try {
                field1 = StatController.class.getDeclaredField("nameId" + j);
                field2 = StatController.class.getDeclaredField("lastNameId" + j);
                field3 = StatController.class.getDeclaredField("dataTimeId" + j);
                field4 = StatController.class.getDeclaredField("questionId" + j);
                field5 = StatController.class.getDeclaredField("answerId" + j);

                field1.setAccessible(true);
                field2.setAccessible(true);
                field3.setAccessible(true);
                field4.setAccessible(true);
                field5.setAccessible(true);

                Label label1 = (Label) field1.get(this);
                Label label2 = (Label) field2.get(this);
                Label label3 = (Label) field3.get(this);
                Label label4 = (Label) field4.get(this);
                Label label5 = (Label) field5.get(this);

                label1.setText(user.getFirstName());
                label2.setText(user.getLastName());
                label3.setText(user.getDataTime());
                label4.setText(String.valueOf(user.getQuestionsAmount()));
                label5.setText(String.valueOf(user.getRightAnswersAmount()));

                field1.setAccessible(false);
                field2.setAccessible(false);
                field3.setAccessible(false);
                field4.setAccessible(false);
                field5.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }*/
}
