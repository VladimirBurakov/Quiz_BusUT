package sample.controllers.reserveCopy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.dao.User;
import sample.db.DatabaseHandler;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatController_old {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dataTimeId3;

    @FXML
    private Label lastNameId1;

    @FXML
    private Label dataTimeId2;

    @FXML
    private Label lastNameId2;

    @FXML
    private Label dataTimeId5;

    @FXML
    private Label lastNameId3;

    @FXML
    private Label dataTimeId4;

    @FXML
    private Label questionId2;

    @FXML
    private Label lastNameId4;

    @FXML
    private Label nameId1;

    @FXML
    private Label questionId1;

    @FXML
    private Label lastNameId5;

    @FXML
    private Label questionId4;

    @FXML
    private Label questionId3;

    @FXML
    private Label nameId4;

    @FXML
    private Label nameId5;

    @FXML
    private Label questionId5;

    @FXML
    private Label nameId2;

    @FXML
    private Label nameId3;

    @FXML
    private Label answerId1;

    @FXML
    private Label answerId2;

    @FXML
    private Label answerId3;

    @FXML
    private Label answerId4;

    @FXML
    private Label answerId5;

    @FXML
    private Label dataTimeId1;

    @FXML
    private Label descriptionLabel;

    @FXML
    void initialize() {

        this.setQuery();

    }
    // рефлексия и даункаст

    private void setQuery(){
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
                field1 = StatController_old.class.getDeclaredField("nameId" + j);
                field2 = StatController_old.class.getDeclaredField("lastNameId" + j);
                field3 = StatController_old.class.getDeclaredField("dataTimeId" + j);
                field4 = StatController_old.class.getDeclaredField("questionId" + j);
                field5 = StatController_old.class.getDeclaredField("answerId" + j);

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
    }

    // напрямую
    /*private void setQuery2(){
        DatabaseHandler dbHandler = new DatabaseHandler();
        ArrayList<User> users = dbHandler.getStat();
        nameId1.setText(users.get(0).getFirstName());
        nameId2.setText(users.get(1).getFirstName());
        nameId3.setText(users.get(2).getFirstName());
        nameId4.setText(users.get(3).getFirstName());
        nameId5.setText(users.get(4).getFirstName());
    }*/
}
