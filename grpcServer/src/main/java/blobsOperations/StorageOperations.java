package blobsOperations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import contract.Contents;
import exceptions.DuplicateBlobException;
import firestore.Database;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;

public class StorageOperations {
    public final Storage storage;
    public String projID;
    private final char[] bucketNameValidChars;
    private final Random random = new Random();
    private final Contents.Flags flag;
    private BlobId blobAdded = null;
    GoogleCredentials credentials;

    public StorageOperations(GoogleCredentials credentials, Contents.Flags flag) {
        this.credentials = credentials;
        StorageOptions storageOptions;
        storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage;
        storage = storageOptions.getService();
        String projID = storageOptions.getProjectId();
        if (projID == null) {
            System.out.println("Keypath/key is not valid!");
            System.exit(-1);
        }
        String validChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        bucketNameValidChars = new char[validChars.length()];
        for (int i = 0; i < validChars.length(); i++) {
            bucketNameValidChars[i] = validChars.charAt(i);
        }
        this.flag = flag;
        this.projID = projID;
        this.storage = storage;
    }

    /**
     * Creates a bucket with name specified on parameter. The try-catch statement is needed because
     * that name could already be taken and .create() will throw exception in that case
     */
    public boolean createBucket(String bucketName) throws Exception {
        int res = hasBucketOnProject(bucketName);
        if (res == 1) return true;
        if (res == -1) return false;
        try {
            storage.create(
                    BucketInfo.newBuilder(bucketName)
                            .setStorageClass(StorageClass.STANDARD)
                            .setLocation(Database.getMainZone())
                            .setVersioningEnabled(false)
                            .build());
            return true;
        } catch (StorageException e) {
            return false; //something went wrong when creating the bucket
        }
    }

    /**
     * Return 1 : bucket exists on project
     * Return 0 : bucket doesn't exist on project
     * Return -1 : bucket exists on GCP storage (on another project)
     */
    public int hasBucketOnProject(String bucketName) {
        try {
            boolean b = storage.get(bucketName, Storage.BucketGetOption.fields()) != null;
            return b ? 1 : 0;
        } catch (StorageException e) {
            return -1; //if it throws exception , means that the bucket exists but not on this project (Permission denied)
        }
    }

    /**
     * Returns true if blob with "blobName" exists on Bucket "bucketName" , for simplification if bucket doesn't exist
     * or exists but on another project then immediately returns false
     */
    public boolean hasBlobOnBucket(String blobName, String bucketName) {
        int res = hasBucketOnProject(bucketName);
        if (res == 1) { //if bucket exists, then checks if blob also exists on that bucket
            BlobId blobId = BlobId.of(bucketName, blobName);
            Blob blob = storage.get(blobId);
            return blob != null;
        } else {
            return false; //means that either the bucket doesn't exist at all , or exists but on another project
        }
    }

    public void uploadBlobToBucket(byte[] arr, String bucketName, String blobName, String contentType) {
        if (arr == null) {
            throw new IllegalArgumentException("Byte array cannot be null!");
        }

        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();

        // When content is not available or large (1MB or more) it is recommended
        // to write it in chunks via the blob's channel writer.
        if (arr.length > 1000000) {
            try (WriteChannel writer = storage.writer(blobInfo)) {
                byte[] buffer = new byte[1024];
                try (InputStream input = new ByteArrayInputStream(arr)) {
                    int limit;
                    while ((limit = input.read(buffer)) >= 0) {
                        //pode dar exeção
                        writer.write(ByteBuffer.wrap(buffer, 0, limit));
                    }
                }
            } catch (Exception e) {
                System.err.println("Could not proceed with operation ");
            }
        } else {
            storage.create(blobInfo, arr);
            this.blobAdded = blobId;
        }
    }

    /**
     * Generates a random alphanumerical string (lowercase letters only because of bucket naming convention) from parameter
     * size. Also has on the first position a "-" char to join it to current bucketName.
     */
    private String getUniqueString(int size) {
        StringBuilder randomString = new StringBuilder();
        randomString.append("-");
        for (int i = 0; i < size; i++) {
            randomString.append(bucketNameValidChars[random.nextInt(bucketNameValidChars.length)]);
        }
        return randomString.toString();
    }

    public String getUniqueBlobName(String blobName, String bucketName) throws DuplicateBlobException {
        boolean b = hasBlobOnBucket(blobName, bucketName);
        if (b && flag == Contents.Flags.KEEP_BOTH) {
            String uniqueBlobName;
            do {
                uniqueBlobName = blobName + getUniqueString(5);
            } while (hasBlobOnBucket(bucketName, uniqueBlobName));
            return uniqueBlobName;
        } else if (b && flag == Contents.Flags.NONE) {
            throw new DuplicateBlobException("Aborted because there is already a blob with name \"" + blobName + "\" on bucket \"" + bucketName + "\" (Use OVERWRITE or KEEP_BOTH flag!)");
        }
        return blobName;
    }

    /**
     * Makes sure that bucketName is available, if yes it creates , if not it generates a unique string pattern and adds to bucketName
     */
    public String tryBucketName(String bucketName) throws Exception {
        boolean bucketCreated;
        //Bucket name must be unique across all GCP storage
        //Bucket name conventions (No Uppercase characters , No whitespace ...) : https://cloud.google.com/storage/docs/naming-buckets
        if (!createBucket(bucketName)) {
            String uniqueBucket;
            do {
                uniqueBucket = bucketName + getUniqueString(10);
                bucketCreated = createBucket(uniqueBucket);
            } while (!bucketCreated);
            return uniqueBucket; // reload the new bucketname
        }
        return bucketName;
    }

    public void rollback() {
        if (blobAdded == null) return;
        storage.delete(blobAdded);
    }
}
