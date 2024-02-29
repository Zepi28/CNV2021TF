package firestore;

import com.google.cloud.Timestamp;

public class Blob {
    public String id;
    public int length;
    public Timestamp date;
    public String contentType;
    public String bucketName;
    public String blobName;

    public Blob(String id, int length, Timestamp date, String contentType, String bucketName, String blobName) {
        this.id = id;
        this.length = length;
        this.date = date;
        this.contentType = contentType;
        this.bucketName = bucketName;
        this.blobName = blobName;
    }
}
