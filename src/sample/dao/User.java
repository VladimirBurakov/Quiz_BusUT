package sample.dao;

public class User {
    private String firstName;
    private String lastName;
    private String dataTime;
    private int questionsAmount;
    private int rightAnswersAmount;

    public User(String firstName, String lastName, String dataTime, int questionsAmount, int rightAnswersAmount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dataTime = dataTime;
        this.questionsAmount = questionsAmount;
        this.rightAnswersAmount = rightAnswersAmount;
    }

    public User(String firstName, String lastName, int questionsAmount, int rightAnswersAmount) {
        this(firstName, lastName, "", questionsAmount, rightAnswersAmount);
    }
    public User(String firstName, String lastName){
        this(firstName, lastName, "", 0, 0);
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(int questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public int getRightAnswersAmount() {
        return rightAnswersAmount;
    }

    public void setRightAnswersAmount(int rightAnswersAmount) {
        this.rightAnswersAmount = rightAnswersAmount;
    }


}
