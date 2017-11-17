import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class BayesianSpamFilter {
    private double spamThreshold;
    private double spamProbability; //
    private int sizeSet;
    public HashMap<String, Word> words = new HashMap<String, Word>();


    public BayesianSpamFilter(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;
    }

    private void probWordSpam(Word W, int cardinalidadS){
        double prob = W.getFrecuencyS();
        prob = prob / cardinalidadS;
        W.setProbabilityS(prob);
    }
    private void probWordNoSpam(Word W, int cardinalidadN){
        double prob = W.getFrecuencyN();
        prob = prob / cardinalidadN;
        W.setProbabilityS(prob);
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
        for(Email m : mails){
            boolean spam = sort(m);
            if(spam == true){
                //Hacer Algo con el Spam
            }
        }
    }

    public void train (List<Email>Spam, List<Email>Normal) {
        int cardinalityS = 0;
        int cardinalityN = 0;

        List<StringTokenizer> allWordsSpam = new ArrayList<>();
        List<StringTokenizer> allWordsNormal = new ArrayList<>();

        for (Email m : Spam){
            StringTokenizer body = new StringTokenizer(m.getBody());
            cardinalityS +=  body.countTokens();
            allWordsSpam.add(body);

        }
        for (Email m : Normal){
            StringTokenizer body = new StringTokenizer(m.getBody());
            cardinalityN +=  body.countTokens();
            allWordsNormal.add(body);
        }
        String word = "";
        // Contamos la frecuencia de las palabras dentro del correo normal
        for (StringTokenizer setOfWords : allWordsNormal){
            while(setOfWords.hasMoreElements()){
               word = setOfWords.nextElement().toString();

               if(words.containsKey(word)){
                    words.get(word).increaseFrecN();
                }else{
                    Word pal = new Word(word);
                    pal.increaseFrecN();
                    words.put(word,pal);
                }
            }
        }
        // Contamos la frecuencia de las palabras dentro del correo Spam
        for (StringTokenizer setOfWordsS : allWordsSpam){
            while(setOfWordsS.hasMoreElements()){
                word = setOfWordsS.nextElement().toString();

                if(words.containsKey(word)){
                    words.get(word).increaseFrecS();
                }else{
                    Word pal = new Word(word);
                    pal.increaseFrecS();
                    words.put(word,pal);
                }
            }
        }

        int finalCardinalityS = cardinalityS;
        int finalCardinalityN = cardinalityN;
        words.forEach((k, v) -> {
            probWordSpam(v, finalCardinalityS);
            probWordNoSpam(v,finalCardinalityN);
        });

        System.out.println("Amount of Spam Emails:" + cardinalityS);
        System.out.println("Amount of Regular Emails:" + cardinalityN);

    }

}
