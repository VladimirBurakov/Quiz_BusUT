package sample.dao;

public class Questions {
    private String question;
    private String [] answers;
    private int result;
    private String number;

    public Questions(String question, String[] answers, int result, String number){
        this.question = question;
        this.answers = answers;
        this.result = result;
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }
    public String[] getAnswers() {
        return answers;
    }
    public int getResult() {
        return result;
    }
    public String getNumber() {
        return number;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
