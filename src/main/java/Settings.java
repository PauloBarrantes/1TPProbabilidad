/**
 * @author B60930&B66605
 */
public class Settings {

    private double SpamThreshold;
    private int sizeSet;
    private double  SpamProbability;

    /**
     *
     */
    public Settings() {

        this.SpamThreshold = 0.9 ;
        this.SpamProbability = 0.3;
        this.sizeSet = 50;
    }

    /**
     *
     * @param st
     */
    public void setSpamThreshold(double st) {
        this.SpamThreshold = st;
    }

    /**
     *
     * @param sp
     */
    public void setSpamProbability(double sp) {
        this.SpamProbability = sp;
    }

    /**
     *
     * @param ss
     */
    public void setSizeSet(int ss) {
        this.sizeSet = ss;
    }

    /**
     *
     * @return SpamThreshold
     */
    public double getSpamThreshold(){
        return this.SpamThreshold;
    }

    /**
     *
     * @return SpamProb
     */
    public double getSpamProbability(){
        return this.SpamProbability;
    }

    /**
     *
     * @return sizeSet
     */
    public int getSizeSet(){
        return this.sizeSet;
    }

}
