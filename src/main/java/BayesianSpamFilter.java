import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class BayesianSpamFilter {
    private double spamThreshold;
    private double spamProbability; //
    private int sizeSet;
    private HashMap<String, Word> pal = new HashMap<String, Word>();


    public BayesianSpamFilter(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;

    }

    private double probWordSpam(Word W, double cardiS){
        double prob = W.getFrecuency();
        prob = prob / cardiS;
        W.setProbability(prob);
        return prob;
    }
    private double probWordNoSpam(Word W, double cardiB){
        double prob = W.getFrecuency();
        prob = prob / cardiB;

        return prob;
    }
    public void recofig(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;
    }

    public boolean sort(Email mail){

        boolean spam = false;
        double prob = 0.0;
        String W = "";
        StringTokenizer body = new StringTokenizer(mail.getBody());
        StringTokenizer header = new StringTokenizer(mail.getHeader());
        while(body.hasMoreElements()){
            W = body.nextElement().toString();
        }
        while(header.hasMoreElements()){
            W = header.nextElement().toString();
        }


        if(prob>=spamThreshold){
            spam = true;
        }
        return spam;
    }

    public void filter(List<Email> mails){
        for(Email i : mails){
            boolean spam = sort(i);
            if(spam == true){
                //Hacer Algo con el Spam
            }
        }
    }

    public void train (List<Email>spam, List<Email>Normal){


    }

}
