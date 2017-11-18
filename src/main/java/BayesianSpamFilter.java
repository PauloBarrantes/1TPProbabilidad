import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
/**
 * @author B60930&B66605
 */
public class BayesianSpamFilter {
    private double spamThreshold;
    private double spamProbability; //
    private int sizeSet;
    public HashMap<String, Word> words = new HashMap<String, Word>();

    /**
     *  Constructor de la clase BayesianSpamFilter
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
     *  Calculamos la probabilidad de que una palabra que entra como parámetro sea Spam
     * @param W
     * @param cardinalidadS
     */
    private void probWordSpam(Word W, int cardinalidadS){
        double prob = W.getFrecuencyS();
        prob = prob / cardinalidadS;
        W.setProbabilityS(prob);
    }

    /**
     * Calculamos la probabilidad de que la palabra que entra como parámetro no sea Spam
     * @param W
     * @param cardinalidadN
     */
    private void probWordNoSpam(Word W, int cardinalidadN){
        double prob = W.getFrecuencyN();
        prob = prob / cardinalidadN;
        W.setProbabilityN(prob);
    }

    /**
     *  Cambiamos las configuraciones del BayesianSpamFilter
     * @param spamThreshold
     * @param spamProbability
     * @param sizeSet
     */
    public void reconfig(double spamThreshold, double spamProbability, int sizeSet){
        this.spamThreshold = spamThreshold;
        this.spamProbability = spamProbability;
        this.sizeSet = sizeSet;
    }

    /**
     *  Cargamos el hashMap que tengamos para realizar los filtros de Spam correspondientes
     * @param words
     */
    public void setWords(HashMap<String, Word> words ){
        this.words = words;
    };

    /**
     * Determinamos por medio de cálculos de la ley de bayes para determinar si un correo es Spam
     * @param mail
     * @return bool
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
        System.out.println("Hashmap Size" + words.size());
        while(body.hasMoreElements()){
            W = body.nextElement().toString();
            if(words.containsKey(W)) {
                if(words.get(W).getProbabilityS() > 0){
                    numerador = numerador * (words.get(W).getProbabilityS());
                    denominador1 = denominador1 * (words.get(W).getProbabilityS() );
                }
                if(words.get(W).getProbabilityN() > 0){
                    denominador2 = denominador2 * (words.get(W).getProbabilityN());
                }

                System.out.println("Numerador " + numerador);
                System.out.println("Denominador1 " +denominador1 );
                System.out.println("D2:" + denominador2);
            }

        }
        denominador1 = denominador1 * spamProbability;
        denominador2 = denominador2 * NoSpamProbability;
        denominador = denominador1 + denominador2;
        prob = numerador/denominador;
        if(prob>=spamThreshold){
            spam = true;
        }
        System.out.println("Email: ");
        System.out.println(mail.getBody());
        System.out.println(prob);
        return spam;
    }

    /**
     * Recibimos una lista de correo, donde filtramos cada uno de ellos
     * @param mails
     */
    public void filter(List<Email> mails){
        for(Email m : mails){
            boolean spam = sort(m);
            if(spam == true){
                System.out.println("Email's probably Spam");
            }else{
                System.out.println("Email's probably not Spam");
            }
        }
    }

    /**
     * Entrenamos el filtro de Spam
     *
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

        System.out.println("Amount of Spam Mail:" + cardinalityS);
        System.out.println("Amount of Regular Mail :" + cardinalityN);

    }

}
