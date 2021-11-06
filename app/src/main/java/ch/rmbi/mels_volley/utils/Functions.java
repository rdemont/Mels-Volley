package ch.rmbi.mels_volley.utils;

public class Functions {
    private Functions(){}
    private static  Functions _instance = null;

    public static Functions getInstance(){
        if (_instance == null){
            _instance = new Functions();
        }
        return _instance ;
    }

    public String getSecondInString(int second){
        String str = "" ;

        long minutes = (second % 3600)/60;
        long seconds = second % 60;

        if (minutes>0 ) {
            if (minutes<=9){
                str += "0";
            }
            str += minutes + ":";
        }
        if (seconds<=9) {
            str += "0";
        }
        str += seconds;
        return str ;
    }

}
