public class ThreadTest03 {
    public static void main(String [] args){
        for(int i = 1;i < 30000;i++){
            if(judgePerfectNumber(i)){
                System.out.println(i);
            }
        }
    }

    public static boolean judgePerfectNumber(int number){
        if(number == 1){
            return false;
        }
        int sum = 1;
        for(int i = 2; i < number; i++){
            if( number % i == 0 ){
                sum = sum + i;
            }
        }
        if( sum == number ){
            return true;
        }else{
            return false;
        }
    }
}
