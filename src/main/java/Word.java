/**
 * @author Paulo Barrantes & Berta Sánchez
 */
public class Word {
    private String word;
    private int frecuencyN;
    private int frecuencyS;

    private double probabilityS;
    private double probabilityN;

    /**
     *
     * @param word
     */
    public Word(String word){
        this.word = word;
        this.frecuencyS = 0;
        this.frecuencyN = 0;
        this.probabilityN = 0;
        this.probabilityS = 0;
    }

    /**
     *
     * @param word
     * @param frecS
     * @param frecN
     * @param probS
     * @param probN
     */
    public Word(String word, int frecS, int frecN, double probS, double probN){
        this.word = word;
        this.frecuencyS = frecS;
        this.frecuencyN = frecN;
        this.probabilityS = probS;
        this.probabilityN = probN;
    }

    /**
     *
     * @param frecuency
     */
    public void setFrecuencyS(int frecuency){
        this.frecuencyS = frecuency;
    }

    /**
     *
     * @param frecuency
     */
    public void setFrecuencyN(int frecuency){
        this.frecuencyN = frecuency;
    }

    /**
     *
     *
     */
    public void increaseFrecS(){
        this.frecuencyS += 1;
    }

    /**
     *
     */
    public void increaseFrecN(){
        this.frecuencyN += 1;
    }

    /**
     *
     * @param prob
     */
    public void setProbabilityS(double prob){
        this.probabilityS = prob;
    }

    /**
     *
     * @param prob
     */
    public void setProbabilityN(double prob){
        this.probabilityN = prob;
    }

    /**
     *
     * @return Word
     */
    public String getWord(){
        return this.word;
    }

    /**
     *
     * @return FrecuencyS
     */

    public int getFrecuencyS(){
        return this.frecuencyS;
    }
    /**
     *
     * @return FrecuencyN
     */
    public int getFrecuencyN(){
        return this.frecuencyN;
    }
    /**
     *
     * @return ProbabilityS
     */
    public double getProbabilityS(){
        return this.probabilityS;
    }
    /**
     *
     * @return ProbabilityN
     */
    public double getProbabilityN(){
        return this.probabilityN;
    }
}
