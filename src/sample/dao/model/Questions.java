package sample.dao.model;

public class Questions {
    private String question;
    private String [] answers;
    private int result;
    private String number;

    private int id;

    public Questions(String question, String[] answers, int result, String number){
        this.question = question;
        this.answers = answers;
        this.result = result;
        this.number = number;
        this.id = 0;
    }

    public Questions(String question, String[] answers, int result, String number, int id){
        this(question, answers, result, number);
        this.id = id;
    }
    public Questions(){
        this.question = "";
        this.answers = null;
        this.result = 0;
        this.number = "";
        this.id = 0;
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
    public int getId() {
        return id;
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
    public void setId(int id) {
        this.id = id;
    }
}
