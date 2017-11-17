import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GmailRetriever {

    public GmailRetriever() {


    }
    //Application name.
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    // Directorio para guardar las credenciales de la aplicación
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/gmail-java-quickstart");

    // Instancia global del File Data Store Factory
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    // Instancia global del JSON Factory
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    // Instancia global del HTTP Transport
    private static HttpTransport HTTP_TRANSPORT;

    // Instancia global para los scopes requeridos
    // Si se modifican los scopes, se deben borrar los credenciales
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

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GmailRetriever.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
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

    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public void logIn(){

    }

    public boolean existCredentials(){
        return false;
    }

    public void logOut(){
        String dir = "Users/Berta/.credentials/gmail-java-quickstart";

        Path filePath = Paths.get(dir);
        try {
            System.out.println("Borrando Credenciales");
            Files.delete(filePath);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public List<Email> getEmail() throws IOException {

      List<Email> list = new ArrayList<Email>();

      Gmail service = getGmailService();

      // Print the labels in the user's account.
      String user = "me";
      String query = "in:Spam";


      ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
      List<Message> messages = new ArrayList<Message>();


      while (response.getMessages() != null) {
          messages.addAll(response.getMessages());
          if (response.getNextPageToken() != null) {
              String pageToken = response.getNextPageToken();
              response = service.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
          } else {
              break;
          }
      }

      for (Message message : messages) {
          //System.out.println(message.toPrettyString());
          Message messagex;
          messagex = service.users().messages().get(user, message.getId()).setFormat("full").execute();
          //Get Body
          byte[] bodyBytes = Base64.decodeBase64(messagex.getPayload().getParts().get(0).getBody().getData().trim().toString());

          String body = new String(bodyBytes, "UTF-8");
          String header = "";

          Email mail = new Email("01", body,"hola","paulo","SpamStalkers");
          list.add(mail);
      }

      return list;
    }



}
