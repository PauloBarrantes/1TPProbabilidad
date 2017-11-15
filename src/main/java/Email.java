import java.util.ArrayList;
import java.util.List;

public class Email {
    private String id;
    private String body;
    private String header;
    private String to;
    private String from;

    public Email (String id, String body, String header, String to, String from) {
       this.id = id;
       this.body = body;
       this.header = header;
       this.to = to;
       this.from = from;
    }
    public String getid(){
        return this.id;
    }

    public String getBody(){
        return this.body;
    }

    public String getHeader(){
        return this.header;
    }
    public String getTo(){
        return this.to;
    }
    public String getFrom(){
        return this.from;
    }

    public List<Email> getEmail(String label){
        List<Email> emails = new ArrayList<>();
        return   emails;
    }


}
