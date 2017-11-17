import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Application {

    private Settings settings = new Settings();
    private BayesianSpamFilter bayesianSpam = new BayesianSpamFilter(settings.getSpamThreshold(), settings.getSpamProbability(), settings.getSizeSet());
    private GmailRetriever gmail = new GmailRetriever();
    private PersistenceManager data = new PersistenceManager();
    private UI ui = new UI();
    private String spam = "in:Spam";
    private String inbox = "in:inbox category:primary";
    private String unread = "in:inbox is:unread category:primary";
    boolean on = true;
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
        if(!gmail.existCredentials()){
            ui.showMenu(1);
            int ans = 0;
            try {
                ans = parseInt(ui.answerS());
            }catch (NumberFormatException e){}

            if(ans == 1){
                logIn();
            }else{
                if(ans == 0){
                    exit();
                }else{
                    System.out.println("Invalid Option");
                }
            }

        }
        do{
            ui.showMenu(2);
            int answerI = 0;
            try {
                answerI = parseInt(ui.answerS());
            }catch (NumberFormatException e){}

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

                case 0:
                    exit();
                    break;
                default:

                    break;
            }
        }while (true);


    }

    private void train(){
        // Cargamos las palabras que tengamos en el hashMap
        try {
            bayesianSpam.train(gmail.getEmail(settings.getSizeSet(),spam),gmail.getEmail(settings.getSizeSet(), inbox));
            data.write(bayesianSpam.words);
            ui.showWords(data.readToShow());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void logIn() throws IOException {
        gmail.logIn();
    }

    private void showWords() throws FileNotFoundException {
        ui.showWords(data.readToShow());
    }

    private void adjustments(){
        ui.settingsMenu(settings.getSpamThreshold(),settings.getSpamProbability(),settings.getSizeSet());
        int answerI = 0;
        try {
            answerI = parseInt(ui.answerS());
        }catch (NumberFormatException e){}

        switch (answerI){
            case 1:
                ui.showSnippet("SpamThreshold:");
                double answerT = 0;
                try {
                    answerT = Double.parseDouble(ui.answerS());
                }catch (NumberFormatException e){}

                if(answerT <= 1 && answerT >=0){
                    settings.setSpamThreshold(answerT);
                    ui.showSnippet("Successful");
                    bayesianSpam.recofig(settings.getSpamThreshold(),settings.getSpamProbability(),settings.getSizeSet());
                }else{
                    ui.showSnippet("No se realizo el cambio");
                }

                break;
            case 2:
                ui.showSnippet("SpamProbability:");
                double answerP = 0;
                try {
                    answerP = Double.parseDouble(ui.answerS());
                }catch (NumberFormatException e){}

                if(answerP>= 0 && answerP <= 1){
                    settings.setSpamProbability(answerP);
                    ui.showSnippet("successful");
                    bayesianSpam.recofig(settings.getSpamThreshold(),settings.getSpamProbability(),settings.getSizeSet());
                }else{
                    ui.showSnippet("No se realizo el cambio");
                }
                break;
            case 3:
                ui.showSnippet("SizeSet:");
                int answerS = 0;
                try {
                    answerS = parseInt(ui.answerS());
                }catch (NumberFormatException e){}
                if(answerS > 0){
                    settings.setSizeSet(answerS);
                    ui.showSnippet("successful");
                    bayesianSpam.recofig(settings.getSpamThreshold(),settings.getSpamProbability(),settings.getSizeSet());
                }else{
                    ui.showSnippet("No se realizo el cambio");
                }

                break;
            case 4:
                ui.showSnippet("No ha realizado ningún cambio");
                break;
            default:
        }
    }

    private void filter() throws IOException {
        if(true){ // Preguntar si el men ya entreno
            bayesianSpam.filter(gmail.getEmail(settings.getSizeSet(),unread));

        } else{
            train();
        }
    }

    private void exit () {
        System.exit(1);
    }

    private void logOut()  {
        try {
            gmail.logOut();
        } catch (IOException e) {

        }
    }

    public static void main (String args[]) {
      Application app = new Application();
    }


}
