public class Application {


    //JAVADOC //Constructor
    public Application(){
    Scanner scanner = new Scanner(System.in);

    }
    public void run(){


        int answerI = 0;
        // Creo que el UI es el que debería comunicarse con el usuario hasta para pedir estas entradas, solo poner llamados a UI y que retornen la respuesta.
        System.out.println("Welcome to the Bayesian Spam Filter ");
        System.out.println(" \( 1 \) Authenticate ");
        System.out.println(" \( 2 \) Exit ");

        String answerS = scanner.nextLine();
        int answerI = Integer.parseInt(answerS);

        if (answerI == 1) {
            logIn();
        }
        if (answerI == 2) {
            exit();
        } else {
            System.out.println("Invalid Option");
        }
    }

    private void train(){

    }

    private void logIn(){

    }

    private void showWords(){

    }

    private void adjustments(){

    }

    private void filter(){


    }

    private void exit () {

    }

    private void logOut(){

    }


}
