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

    public GmailRetriever()  {

    }

    /**
     * Application name.
     */


    private static final String APPLICATION_NAME = "BayesianSpamFilter";
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/BayesianSpamFilter");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_READONLY);

   static {
       try {
          HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
           DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR); // Está linea da problemas
      } catch (Throwable t) {
           System.out.println("Se salió");
        t.printStackTrace();
        System.exit(1);
    }
   }

    private static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = GmailRetriever.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

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

    public void logIn() throws IOException {
        String user = "me";
        Gmail service = getGmailService();
        System.out.println("Aquí nice");
    }

    public boolean existCredentials(){
        return false;
    }

    /**
     *
     */
    public void logOut(){
        String dir = DATA_STORE_DIR.getAbsolutePath();

        Path filePath = Paths.get(dir);
        try {
            System.out.println("Borrando Credenciales");
            Files.delete(filePath); // Se cae acá
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     * @return
     * @throws IOException
     */
    public List<Email> getEmail() throws IOException {

      List<Email> list = new ArrayList<>();

      Gmail service = getGmailService();

      // Print the labels in the user's account.
      String user = "me";
      String query = "in:Spam";

        System.out.println("1");

      ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
      List<Message> messages = new ArrayList<>();
        System.out.println("2");

      while (response.getMessages() != null) {
          System.out.println("3");

          messages.addAll(response.getMessages());
          if (response.getNextPageToken() != null) {
              String pageToken = response.getNextPageToken();
              response = service.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
          } else {
              break;
          }
      }
        System.out.println("4");

      for (Message message : messages) {
          //System.out.println(message.toPrettyString());
          System.out.println("5.1");
          Message messagex;
          System.out.println("5.2");

          messagex = service.users().messages().get(user, message.getId()).setFormat("full").execute();
          System.out.println("5.3");

          //Get Body
          byte[] bodyBytes = Base64.decodeBase64(messagex.getPayload().getParts().get(0).getBody().getData().trim().toString());
          System.out.println("5.4");


          String body = new String(bodyBytes, "UTF-8");
          System.out.println("5.5");

          String header = "Yeah";

          Email mail = new Email("01", body,header,"paulo","SpamStalkers");
          System.out.println(body);

          list.add(mail);
      }

      return list;
    }



}
