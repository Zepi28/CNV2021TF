package ctrl;

import blobsOperations.StorageOperations;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import contract.Contents;
import contract.ResultUpload;
import exceptions.DuplicateBlobException;
import firestore.Blob;
import firestore.Database;
import firestore.KeyCredential;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import pubsub.PubSub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ServerStreamObserver implements StreamObserver<Contents> {
    private final StreamObserver<ResultUpload> responseObserver;
    private int blockNum;
    private String filename;
    private String contentType;
    private Contents.Flags flag;
    byte[] finalArr;

    public ServerStreamObserver(StreamObserver<ResultUpload> responseObserver) throws IOException, ExecutionException, InterruptedException {
        this.responseObserver = responseObserver;
        this.blockNum = 0;
    }

    @Override
    public void onNext(Contents contents) {
        if (blockNum == 0) {
            filename = contents.getFilename();
            contentType = contents.getContentType();
            flag = contents.getFlag();
        }
        byte[] cont = contents.getFileBlockBytes().toByteArray();
        System.out.println("Content block (length of bytes): " + cont.length);
        this.joinToFinalArray(cont);
        blockNum++;
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call : " + throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        StorageOperations storageOperations = null;
        try {
            GoogleCredentials credentials = KeyCredential.getGoogleCredentialsInstance();
            Database.init(credentials);
            storageOperations = new StorageOperations(credentials, flag);

            String defaultBucketName = "bucket-cn-trabalho-final";
            String bucketName = storageOperations.tryBucketName(defaultBucketName);

            String blobName = storageOperations.getUniqueBlobName(filename, bucketName);
            storageOperations.uploadBlobToBucket(finalArr, bucketName, blobName, contentType);

            //Making Hash of "fullName"
            String fullName = blobName + bucketName;
            String hashId = getHash(fullName);

            Database.insertToDatabase(new Blob(hashId, finalArr.length, Timestamp.now(), contentType, bucketName, blobName), flag == Contents.Flags.OVERWRITE);

            new PubSub().publishMessage(hashId);

            awaitTranslatedResponseAndSend(hashId,fullName);

            responseObserver.onCompleted();
            System.out.println("Stream completed!");
        } catch (IOException e) { //Nothing was added , no rollbacks needed here
            Throwable th = new StatusException(Status.NOT_FOUND.withDescription(".JSON not found!"));
            responseObserver.onError(th);
        } catch (DuplicateBlobException e) { //Also no rollbacks needed here
            Throwable th = new StatusException(Status.INVALID_ARGUMENT.withDescription(e.getMessage()));
            responseObserver.onError(th);
        } catch (Exception e) { //To be safe , rollbacks everything (if exists)
            Throwable th = new StatusException(Status.UNKNOWN.withDescription(e.getMessage()));
            responseObserver.onError(th);
            rollback(storageOperations);
        } finally {
            try {
                Database.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void awaitTranslatedResponseAndSend(String hashId, String fullname) throws Exception {
        LinkedList<Label> listLabels;
        for (int i = 0; i < 15; i++) {
            TimeUnit.SECONDS.sleep(1);
            try {
                if ((listLabels = Database.getTranslatedResults(hashId)) != null) {
                    for (Label lb : listLabels) {
                        responseObserver.onNext(
                                ResultUpload.newBuilder()
                                        .setLabel(lb.toString())
                                        .setFilename(fullname)
                                        .build());
                    }
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        throw new Exception("Could not get translated results!");
    }

    /**
     * If something goes wrong rollbacks the insertions (only if any insertion was made obviously)
     */
    private void rollback(StorageOperations storageOperations) {
        if (storageOperations != null) {
            storageOperations.rollback();
        }
        Database.rollback();
    }

    public static String getHash(String fullName) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = algorithm.digest(fullName.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }

    public void joinToFinalArray(byte[] toAdd) throws IllegalArgumentException {
        if (toAdd == null)
            throw new IllegalArgumentException();
        if (finalArr == null) {
            finalArr = Arrays.copyOf(toAdd, toAdd.length);
        } else {
            byte[] newArr = new byte[finalArr.length + toAdd.length];
            System.arraycopy(finalArr, 0, newArr, 0, finalArr.length);
            System.arraycopy(toAdd, 0, newArr, finalArr.length, toAdd.length);
            finalArr = newArr;
        }
    }
}