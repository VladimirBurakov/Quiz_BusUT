package sample.services;

public class CheckHelper {
    public static boolean isRealDigit(String number){
        if(number == null || number.isEmpty()){
            return false;
        }
        for(int i = 0; i < number.length(); i++){
            if(!Character.isDigit(number.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static boolean isRightRange(String number, int start, int end){
        int quantity;

            if(!CheckHelper.isRealDigit(number)){
                FXMLHelper.setMessage("Пожалуйста введите \n" +
                        "целое число\n" +
                        " от "+ start +" до "+ end +".");
                FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
            }else{
                quantity = Integer.parseInt(number);
                if(quantity >= start && quantity <= end){
                    return true;
                }else{
                    FXMLHelper.setMessage("Пожалуйста введите \n" +
                                         "целое число\n" +
                            " от "+ start +" до "+ end +".");
                    FXMLHelper.loadPage("/sample/views/messageWindow.fxml");
                }
            }
            return false;
    }
}
