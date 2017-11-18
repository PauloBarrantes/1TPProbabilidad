import java.util.ArrayList;
import java.util.List;
/**
 * @author Paulo Barrantes & Berta Sánchez
 */
public class Email {
    private String id;
    private String body;
    private String header;
    private String to;
    private String from;

    /**
     *
     * @param id
     * @param body
     * @param header
     * @param to
     * @param from
     */
    public Email (String id, String body, String header, String to, String from) {
       this.id = id;
       this.body = body;
       this.header = header;
       this.to = to;
       this.from = from;
    }

    /**
     *
     *
     * @return Id
     */
    public String getid(){
        return this.id;
    }

    /**
     *
     *
     *
     * @return Body
     */
    public String getBody(){
        return this.body;
    }

    /**
     *
     *
     * @return Header
     */
    public String getHeader(){
        return this.header;
    }

    /**
     *
     * @return To
     */
    public String getTo(){
        return this.to;
    }

    /**
     *
     * @return From
     */
    public String getFrom(){
        return this.from;
    }

    /**
     *
     * @param label
     * @return Emails
     */
    public List<Email> getEmail(String label){
        List<Email> emails = new ArrayList<>();
        return   emails;
    }


}
