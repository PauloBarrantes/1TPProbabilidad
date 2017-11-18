import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailRetriever {

    public GmailRetriever()  {

    }

    protected Gmail service;
    protected String user = "me";
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "BayesianSpamFilter";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/BayesianSpamFilter");
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_READONLY);

   static {
       try {
          HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
          DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
      } catch (Throwable t) {
        t.printStackTrace();
        System.exit(1);
    }
   }

    /**
     * Creates credentials given that the user has a allowed the app to do so.
     * @return credential
     * @throws IOException
     */
    private static Credential authorize() throws IOException {

        InputStream in = GmailRetriever.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));


        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Authorises the credentials through the Gmail Services.
     * @return Gmail.Builder
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Links the user to the credentials, through the Gmail Services.
     * @throws IOException
     */
    public void logIn() throws IOException {
      user = "me";
      service = getGmailService();
    }

    /**
     * Creates a path to were the credentials had been saved, and proceeds to delete them afterwards, that way the user succesfully logs out. 
     * @throws IOException
     */
    public void logOut() throws IOException {
        String path = System.getProperty("user.home") + "/.credentials/BayesianSpamFilter/StoredCredential";
        //String path = DATA_STORE_DIR.getAbsolutePath();
        File file = new File(path);
        System.out.println(path);
        System.out.println("Loging Out...");
        file.delete();
        service = null;
    }

    /**
     * List all Messages of the user's mailbox matching the query.
     *
     * @param size Authorized Gmail API instance.
     * @param label
     * @throws IOException
     */
    public List<Email> getEmail(int size, String label) throws IOException {

      List<Email> list = new ArrayList<>();

      // Print the labels in the user's account.
      user = "me";

        ListMessagesResponse response = service.users().messages().list(user).setQ(label).execute();
      List<Message> messages = new ArrayList<>();

      while (response.getMessages() != null) {

          messages.addAll(response.getMessages());
          if (response.getNextPageToken() != null) {
              String pageToken = response.getNextPageToken();
              response = service.users().messages().list(user).setQ(label).setPageToken(pageToken).execute();
          } else {
              break;
          }
      }
        // The array we are using  has a size of 160, our spam folder has only 33 messages.
      int counter = 0;
    for (Message message : messages) {
        try {
            if(counter <= size){
                Message messagex;
                messagex = service.users().messages().get(user, message.getId()).setFormat("full").execute();
                //Get Body
                byte[] bodyBytes = Base64.decodeBase64(messagex.getPayload().getParts().get(0).getBody().getData().trim().toString());
                String body = new String(bodyBytes, "UTF-8");
                String header = "Yeah";
                Email mail = new Email(" ", body,header," "," ");
                list.add(mail);
                ++counter;
            }
        }catch (NullPointerException ignored){
        }
      }

      return list;
    }



}
