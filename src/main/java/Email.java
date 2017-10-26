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

    public void logIn(){

    }
    public void logOut(){

    }
    public List<Email> getEmail(String label){
        List<Email> emails;
        return   emails;
    }


}
