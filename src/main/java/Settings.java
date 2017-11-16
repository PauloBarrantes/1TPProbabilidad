public class Settings {

    private double SpamThreshold;
    private int sizeSet;
    private double  SpamProbability;


    public Settings() {

        this.SpamThreshold = 0.9 ;
        this.SpamProbability = 0.3;
        this.sizeSet = 50;


    }

    public void setSpamThreshold(double st) {
        this.SpamThreshold = st;
    }

    public void setSpamProbability(double sp) {
        this.SpamProbability = sp;
    }

    public void setSizeSet(int ss) {
        this.sizeSet = ss;
    }


    public double getSpamThreshold(){
        return this.SpamThreshold;
    }

    public double getSpamProbability(){
        return this.SpamProbability;
    }

    public int getSizeSet(){
        return this.sizeSet;
    }
}
