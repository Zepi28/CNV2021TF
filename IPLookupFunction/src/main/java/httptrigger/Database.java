package httptrigger;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class Database {
    private static final String COLLECTION_NAME = "Information";
    private static final String PRIVATE_KEY = "/key.json";
    private static final String DOCUMENT_NAME = "InstanceGroupServers";
    private static String instanceGroupName = null;
    private static String zone = null;

    public void setParametersFromDB() throws Exception {
        InputStream serviceAccount = getClass().getResourceAsStream(PRIVATE_KEY);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions options = FirestoreOptions
                .newBuilder().setCredentials(credentials).build();
        try (Firestore db = options.getService()) {
            DocumentReference docRef = db.collection(COLLECTION_NAME).document(DOCUMENT_NAME);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document;
            document = future.get();
            if (document.exists()) {
                instanceGroupName = (String) document.get("Name");
                zone = (String) document.get("Zone");
            }
            System.out.println(instanceGroupName);
            System.out.println(zone);
            if (instanceGroupName == null || zone == null) {
                throw new IllegalArgumentException("Cannot find \"Name\" or \"Zone\" on Document " + DOCUMENT_NAME);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getInstanceGroupName() {
        return instanceGroupName;
    }

    public String getZone() {
        return zone;
    }
}
