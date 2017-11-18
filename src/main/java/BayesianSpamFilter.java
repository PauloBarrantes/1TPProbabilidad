import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author PauloBarrantes&BertaSánchez
 *
 *
 */
public class BayesianSpamFilter {
    private double spamThreshold;
    private double spamProbability; //
    private int sizeSet;
    public HashMap<String, Word> words = new HashMap<String, Word>();

    /**
     *
     *
     * @param spamThreshold
     * @param spamProbability
     * @param sizeSet
     */
    public BayesianSpamFilter(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;
    }

    /**
     *
     *
     *
     * @param W
     * @param cardinalidadS
     */
    private void probWordSpam(Word W, int cardinalidadS){
        double prob = W.getFrecuencyS();
        prob = prob / cardinalidadS;
        W.setProbabilityS(prob);
    }

    /**
     *
     *
     * @param W
     * @param cardinalidadN
     */
    private void probWordNoSpam(Word W, int cardinalidadN){
        double prob = W.getFrecuencyN();
        prob = prob / cardinalidadN;
        W.setProbabilityN(prob);
    }

    /**
     *
     * @param spamThreshold
     * @param spamProbability
     * @param sizeSet
     */
    public void reconfig(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;
    }
    /*

     */
    public void setWords(HashMap<String, Word> words ){
        this.words = words;
    };

    /**
     *
     *
     * @param mail
     * @return boolean spam
     */
    public boolean sort(Email mail){
        boolean spam = false;
        double prob = 1.0;
        double numerador = 1.0;
        double denominador1 = 1.0;
        double denominador2 = 1.0;
        double denominador = 1.0;

        String W = " ";
        StringTokenizer body = new StringTokenizer(mail.getBody());
        StringTokenizer header = new StringTokenizer(mail.getHeader());
        double NoSpamProbability = 1-spamProbability;
        System.out.println("Tamaño del hashmap" + words.size());
        System.out.println("Hashmap Size" + words.size());
        while(body.hasMoreElements()){
            W = body.nextElement().toString();
            if(words.containsKey(W)) {
                if(words.get(W).getProbabilityS() > 0){
                    numerador = numerador * (words.get(W).getProbabilityS() * spamProbability);
                    denominador1 = denominador1 * (words.get(W).getProbabilityS() * spamProbability);
                }
                if(words.get(W).getProbabilityN() > 0){
                    denominador2 = denominador2 * (words.get(W).getProbabilityN() * NoSpamProbability);
                }

                System.out.println("Numerador " + numerador);
                System.out.println("Denominador1 " +denominador1 );
                System.out.println("D2:" + denominador2);
            }

        }
        denominador = denominador1 + denominador2;
        prob = numerador/denominador;
        if(prob>=spamThreshold){
            spam = true;
        }
        System.out.println("Correo: ");
        System.out.println("Email: " + count);
        System.out.println(mail.getBody());
        System.out.println(prob);

        return spam;
    }

    /**
     *
     * @param mails
     */
    public void filter(List<Email> mails){
        for(Email m : mails){
            boolean spam = sort(m);
            if(spam == true){
                System.out.println("El mail probablemente es Spam");
                System.out.println("Email's probably Spam");
            }else{
                System.out.println("El mail probablemente no es Spam");
                System.out.println("Email's probably not Spam");
            }
        }
    }

    /**
     *
     * @param Spam
     * @param Normal
     */
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

        System.out.println("Cardinalidad Spam:" + cardinalityS);
        System.out.println("Cardinalidad Normal:" + cardinalityN);
        System.out.println("Amount of Spam Mail:" + cardinalityS);
        System.out.println("Amount of Regular Mail :" + cardinalityN);

    }

}
