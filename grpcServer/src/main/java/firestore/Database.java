package firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import ctrl.Label;
import exceptions.FirestoreDuplicateException;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Database {

    private static Firestore db;
    private static final String STORAGE_COLLECTION = "ComputeStorage";
    public static final String INFO_COLLECTION = "Information";
    public static String TRANSLATE_COLLECTION;
    private static DocumentReference documentAdded = null;

    /**
     * Get Google Application Credentials
     * Update currentCollection variable to get Firestore Collection name
     */
    public static void init(GoogleCredentials credentials) throws ExecutionException, InterruptedException {
        FirestoreOptions options = FirestoreOptions
                .newBuilder().setCredentials(credentials).build();
        db = options.getService();
        TRANSLATE_COLLECTION = getParameter("TranslateCollection", "FireStore", INFO_COLLECTION);
    }

    public static String getParameter(String parameterName, String documentName) throws IllegalArgumentException, ExecutionException, InterruptedException {
        String COLLECTION_NAME = "Information";
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document;
        document = future.get();
        if (document.exists()) {
            return (String) document.get(parameterName);
        } else {
            throw new IllegalArgumentException("Could not find parameter " + parameterName + " on document " + documentName);
        }
    }

    public static void insertToDatabase(Blob blob, boolean overwrite) throws ExecutionException, InterruptedException, FirestoreDuplicateException {
        DocumentReference docRef = db.collection(STORAGE_COLLECTION).document(blob.id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Map<String, Object> updates = new HashMap<>();
        updates.put("date", blob.date);
        updates.put("length", blob.length);
        updates.put("blobName", blob.blobName);
        updates.put("bucketName", blob.bucketName);
        updates.put("contentType", blob.contentType);
        if (!document.exists()) {
            docRef.set(updates);
            System.out.println("Insertion to database!");
            documentAdded = docRef;
        } else if (document.exists() && overwrite) {
            docRef.update(updates);
            System.out.println("Update to database!");
            documentAdded = docRef;
        } else {
            //Means that firestore has already "blob.id" while compute storage didn't have the respective blob+bucket object.
            //If this is thrown means that either the hash function returned the same ID for different objects OR
            //a user deleted the blob on compute storage but forgot to delete the respective ID from firestore
            throw new FirestoreDuplicateException("FireStore already has the ID : \"" + blob.id
                    + "\" but bucket \"" + blob.bucketName + "\" and blob \"" + blob.blobName + "\" doesn't exist!");
        }
    }

    public static void close() throws Exception {
        documentAdded = null;
        db.close();
    }

    /**
     * Example : MainZone = "europe-west2" , while on database parameter Zone has fullzone like "europe-west2-c"
     * so we need to remove the subregion "-c"
     */
    public static String getMainZone() throws ExecutionException, InterruptedException {
        String mainZone = Database.getParameter("Zone", "InstanceGroupServers");
        return mainZone.substring(0, mainZone.lastIndexOf("-"));
    }

    public static void rollback() {
        if (documentAdded == null) return;
        documentAdded.delete();
    }

    public static String getParameter(String parameterName, String documentName, String collection) throws IllegalArgumentException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document;
        document = future.get();
        if (document.exists()) {
            return (String) document.get(parameterName);
        } else {
            throw new IllegalArgumentException("Could not find parameter " + parameterName + " on document " + documentName);
        }
    }

    public static LinkedList<DocumentSnapshot> getAllDocuments(Timestamp dateBeg, Timestamp dateEnd) throws Exception {
        ApiFuture<QuerySnapshot> future =
                db.collection(STORAGE_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        LinkedList<DocumentSnapshot> docList = new LinkedList<>();
        for (DocumentSnapshot doc : documents) {
            Object time = doc.get("date");
            if (time != null) {
                Timestamp timestamp = (Timestamp) time;
                if (timestamp.compareTo(dateBeg) > 0 && timestamp.compareTo(dateEnd) < 0) {
                    docList.add(doc);
                }
            }
        }
        if (docList.isEmpty()) {
            throw new Exception("No files found between that range (" + dateBeg + "," + dateEnd + ")");
        }
        return docList;
    }

    public static LinkedList<Label> getTranslatedResults(String docID) {
        try {
            DocumentReference docRef = db.collection(TRANSLATE_COLLECTION).document(docID);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            Map<String, Object> map = document.getData();
            if (map == null)
                throw new NullPointerException("No fields");
            LinkedList<Label> list = new LinkedList<>();
            for (String label : map.keySet()) {
                try {
                    ArrayList<?> arr = (ArrayList<?>) map.get(label); //Obtains array
                    double score = (double) arr.get(0); //Obtains the first (and only , for now) member , which is a Double
                    list.add(new Label(label, score));
                } catch (ClassCastException e) {
                    list.add(new Label((String) map.get(label)));
                }
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean hasLabel(String docID, String label) {
        LinkedList<Label> results = getTranslatedResults(docID);
        if (results == null) return false;
        for (Label labelResult : results) {
            if (labelResult.isLabel(label)) {
                return true;
            }
        }
        return false;
    }
}
