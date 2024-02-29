package firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import ctrl.PairLabel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Database {

    private static Firestore db;
    public static String LABELS_COLLECTION;
    public static String TRANSLATE_COLLECTION;
    public static final String INFO_COLLECTION = "Information";
    private final static String PRIVATE_KEY = "/key.json";

    /**
     * Get Google Application Credentials
     * storageCollection = with ID , gets BucketName and BlobName
     * labelsCollection = where it sets the labels
     */
    public static void init() throws IOException, ExecutionException, InterruptedException {
        FirestoreOptions options = FirestoreOptions
                .newBuilder().setCredentials(getGoogleCredentialsInstance()).build();
        db = options.getService();
        LABELS_COLLECTION = Database.getParameter("LabelsCollection", "FireStore", INFO_COLLECTION);
        TRANSLATE_COLLECTION = Database.getParameter("TranslateCollection", "FireStore", INFO_COLLECTION);
    }

    public static GoogleCredentials getGoogleCredentialsInstance() throws IOException {
        InputStream serviceAccount = Database.class.getResourceAsStream(PRIVATE_KEY);
        return GoogleCredentials.fromStream(serviceAccount);
    }

    public static DocumentSnapshot getDocumentSnapshot(String documentName, String collection) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        return future.get();
    }

    public static String getParameter(String parameterName, String documentName, String collection) throws IllegalArgumentException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return (String) document.get(parameterName);
        } else {
            throw new IllegalArgumentException("Could not find parameter " + parameterName + " on document " + documentName);
        }
    }

    public static void insertToDatabase(List<PairLabel> labels, String docID) throws ExecutionException, InterruptedException {
        if (labels == null) return;
        DocumentReference docRef = db.collection(TRANSLATE_COLLECTION).document(docID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Map<String, Object> updates = new HashMap<>();
        for (PairLabel currLabel : labels) {
            updates.put(currLabel.label, Collections.singletonList(currLabel.score));
        }
        setDocument(docRef, document, updates);
    }

    public static void insertErrorToDatabase(String errorMessage, String docID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(TRANSLATE_COLLECTION).document(docID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Map<String, Object> updates = new HashMap<>();
        updates.put("Error", errorMessage);
        setDocument(docRef, document, updates);
    }

    private static void setDocument(DocumentReference docRef, DocumentSnapshot document, Map<String, Object> updates) {
        if (!document.exists()) {
            docRef.set(updates);
        } else {
            docRef.update(updates);
        }
    }

    public static void close() throws Exception {
        db.close();
    }
}
