public class Word {
    private String word;
    private int frecuencyN;
    private int frecuencyS;

    private double probabilityS;
    private double probabilityN;
    public Word(String word){
        this.word = word;
        this.frecuencyS = 0;
        this.frecuencyN = 0;
        this.probabilityN = 0;
        this.probabilityS = 0;
    }
    public Word(String word, int frecS, int frecN, double probS, double probN){
        this.word = word;
        this.frecuencyS = frecS;
        this.frecuencyN = frecN;
        this.probabilityS = probS;
        this.probabilityN = probN;
    }
    public void setFrecuencyS(int frecuency){
        this.frecuencyS = frecuency;
    }
    public void setFrecuencyN(int frecuency){
        this.frecuencyN = frecuency;
    }
    public void increaseFrecS(){
        this.frecuencyS += 1;
    }
    public void increaseFrecN(){
        this.frecuencyN += 1;
    }

    public void setProbabilityS(double prob){
        this.probabilityS = prob;
    }
    public void setProbabilityN(double prob){
        this.probabilityN = prob;
    }
    public String getWord(){
        return this.word;
    }

    public int getFrecuencyS(){
        return this.frecuencyS;
    }

    public int getFrecuencyN(){
        return this.frecuencyN;
    }

    public double getProbabilityS(){
        return this.probabilityS;
    }
    public double getProbabilityN(){
        return this.probabilityN;
    }
}
