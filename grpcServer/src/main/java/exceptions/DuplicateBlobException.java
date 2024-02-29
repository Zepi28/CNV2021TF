package exceptions;

public class DuplicateBlobException extends Exception {
    public DuplicateBlobException(String msg) {
        super(msg);
    }
}
