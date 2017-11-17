import java.util.List;
import java.util.Scanner;
public class UI {

    private Scanner scanner = new Scanner(System.in);

    public UI(){


    }
    public int answerI(){
        String answerS = scanner.nextLine();
        int answerI = Integer.parseInt(answerS);
        return answerI;
    }
    public void showMenu(int menu){
        if(menu == 1){
            System.out.println("Welcome to the Bayesian Spam Filter ");
            System.out.println(" ( 1 ) Authenticate ");
            System.out.println(" ( 2 ) Exit ");
        }else{
            if(menu == 2){
                System.out.println("Choose an Option: ");
                System.out.println(" ( 1 ) Settings");
                System.out.println(" ( 2 ) Train");
                System.out.println(" ( 3 ) Show Data");
                System.out.println(" ( 4 ) Get New Mail ");
                System.out.println(" ( 5 ) Log Out ");
                System.out.println(" ( 6 ) Exit ");

            }
        }



    }

    public void inputUser(){

    }

    public void showWords(List<Word> words) {

    }

    public void showSnippet(String snippet){
        System.out.println(snippet);
    }
}
