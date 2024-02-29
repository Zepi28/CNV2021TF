package firestore;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;

public class KeyCredential {
    private final static String PRIVATE_KEY = "/key.json";

    public static GoogleCredentials getGoogleCredentialsInstance() throws IOException {
        InputStream serviceAccount = KeyCredential.class.getResourceAsStream(PRIVATE_KEY);
        return GoogleCredentials.fromStream(serviceAccount);
    }
}
