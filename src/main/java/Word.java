public class Word {
    private String word;
    private double frecuency;
    private double probability;
    public Word(String word, double frecuency, double probability ){
        this.word = word;
        this.frecuency = frecuency;
        this.probability = probability;
    }
    public void setFrecuency(double frecuency){
        this.frecuency = frecuency;
    }
    public void increaseFrec(){
        this.frecuency = frecuency+1;
    }

    public void setProbability(double prob){
        this.probability = probability;
    }
    public String getWord(){
        return this.word;
    }

    public double getFrecuency(){
        return this.frecuency;
    }

    public double getProbability(){
        return this.probability;
    }
}
