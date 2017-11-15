import java.util.List;

public class UI {

    Scanner scanner = new Scanner(System.in);
    Application app = new Application();

    public UI(){


    }
    public void showMenu(){

        System.out.println("Welcome to the Bayesian Spam Filter ");
        System.out.println(" \( 1 \) Authenticate ");
        System.out.println(" \( 2 \) Exit ");

        String answerS = scanner.nextLine();
        int answerI = Integer.parseInt(answerS);

        if (answerI == 1) {
            app.logIn();
        }
        if (answerI == 2) {
            app.exit();
        } else {
            System.out.println("Invalid Option");
        }

        System.out.println("Choose an Option: ");
        System.out.println(" \( 1 \) Settings");
        System.out.println(" \( 2 \) Train");
        System.out.println(" \( 3 \) Settings");
        System.out.println(" \( 4 \) Show Data");
        System.out.println(" \( 5 \) Get New Mail ");
        System.out.println(" \( 6 \) Log Out ");
        System.out.println(" \( 7 \) Exit ");

        answerS = scanner.nextLine();
        answerI = Integer.parseInt(answerS);

        switch (answerI) {

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;
        }

    }

    public void inputUser(){

    }

    public void showWords(List<Word> words) {
       words.forEach(
         system.out.println("Hola");
       );
    }

    public void showSnippet(String snippet){

    }
}
