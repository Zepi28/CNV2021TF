package exceptions;

public class FirestoreDuplicateException extends Exception {
    //Found a duplicate ID on Firestore , no insertion made on DB , only the Blob needs to be rolled back
    public FirestoreDuplicateException(String msg) {
        super(msg);
    }
}
