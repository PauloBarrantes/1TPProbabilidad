import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Application {

    private Settings configuration = new Settings();
    private BayesianSpamFilter bayesianSpam = new BayesianSpamFilter(configuration.getSpamThreshold(), configuration.getSpamProbability(), configuration.getSizeSet());
    private GmailRetriever gmail = new GmailRetriever();
    private PersistenceManager data = new PersistenceManager();
    private UI ui = new UI();

    //JAVADOC //Constructor
    public Application() {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run() throws IOException {
        // Si las credenciales no existen, hacemos el llamado al login
        if(gmail.existCredentials() == false){
            ui.showMenu(1);
            int ans = ui.answerI();
            if(ans == 1){
                gmail.logIn();
            }else{
                if(ans == 2){
                    exit();
                }else{
                    System.out.println("Invalid Option");
                }
            }

        }else{



        }
        ui.showMenu(2);
        int answerI = ui.answerI();

        switch (answerI) {

            case 1:
                adjustments();
                break;

            case 2:
                train();
                break;

            case 3:
                showWords();
                break;

            case 4:
                filter();
                break;

            case 5:
                logOut();
                break;

            case 6:
                exit();
                break;


        }

    }

    private void train(){


    }

    void logIn(){

    }

    private void showWords() throws FileNotFoundException {
        ui.showWords(data.readToShow());
    }

    private void adjustments(){

    }

    private void filter() throws IOException {
        List<Email> mails = gmail.getEmail();
        for (Email i : mails){
            System.out.println(i.getBody());
        }
        //bayesianSpam.filter(gmail.getEmail());
    }

    private void exit () {
        System.exit(1);
    }

    private void logOut(){
        gmail.logOut();
    }

    public static void main (String args[]) {
      Application app = new Application();
    }


}
