package ctrl;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.ByteString;

import contract.RequestFiles;
import contract.ResultFiles;
import firestore.Database;
import firestore.KeyCredential;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import pubsub.PubSub;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class ListFilesStream {
    private final StreamObserver<ResultFiles> responseObserver;
    private final String date1;
    private final String date2;
    private final String label;
    private boolean toDownload;
    private RequestFiles request;
    private final static int MAX_BLOCK_SIZE = 1024;

    public ListFilesStream(RequestFiles request, StreamObserver<ResultFiles> responseObserver) {
        this.responseObserver = responseObserver;
        this.request = request;
        this.date1 = request.getDate1();
        this.date2 = request.getDate2();
        this.label = request.getLabel();
        this.toDownload = request.getDownload();
        run();
    }

    private void run() {
        try {
            Database.init(KeyCredential.getGoogleCredentialsInstance());
            if (date1 == null || date2 == null) {
                throw new NullPointerException("Dates cannot be null!");
            }
            Timestamp timestamp1 = getDate(date1);
            Timestamp timestamp2 = getDate(date2);
            LinkedList<DocumentSnapshot> docList = Database.getAllDocuments(timestamp1, timestamp2);
            for (DocumentSnapshot doc : docList) {
                //Here instead of always translating , we could have a mechanism
                // to detect if TranslateResults already has the results but since images can change
                // at anytime (meaning the results will also change) it's better to translate again
                // just to make sure the results are updated
                new PubSub().publishMessage(doc.getId());
            }
            boolean hasResults = false;
            for (DocumentSnapshot doc : docList) {
                String bucketName = (String) doc.get("bucketName");
                String blobName = (String) doc.get("blobName");
                String filename = bucketName + "/" + blobName;
                if (Database.hasLabel(doc.getId(), label)) {
                    if (toDownload) {
                        //download docList
                        byte[] fileBytes = downloadDocument(bucketName, blobName);
                        sendFile(filename, fileBytes);
                        hasResults = true;
                    } else {
                        responseObserver.onNext(ResultFiles.newBuilder()
                                .setFilename(filename)
                                .build());
                        hasResults = true;
                    }
                }
            }
            if (!hasResults) {
                Throwable th = new StatusException(Status.NOT_FOUND.withDescription("No results found with that label inside that Date range!"));
                responseObserver.onError(th);
            } else {
                responseObserver.onCompleted();
                System.out.println("Stream completed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Throwable th = new StatusException(Status.UNKNOWN.withDescription(e.getMessage()));
            responseObserver.onError(th);
        }
    }

    private void sendFile(String filename, byte[] fileContent) {
        if (MAX_BLOCK_SIZE >= fileContent.length) {
            responseObserver.onNext(ResultFiles.newBuilder()
                    .setTotalBlocks(1)
                    .setData(ByteString.copyFrom(fileContent))
                    .setFilename(filename)
                    .build());
        } else {
            int idx = 0;
            int remainingBytes = fileContent.length;
            int offset;
            while (remainingBytes != 0) {
                if (remainingBytes >= MAX_BLOCK_SIZE) {
                    offset = MAX_BLOCK_SIZE;
                    remainingBytes -= MAX_BLOCK_SIZE;
                } else {
                    offset = remainingBytes;
                    remainingBytes = 0;
                }
                responseObserver.onNext(ResultFiles.newBuilder()
                        .setTotalBlocks(1)
                        .setData(ByteString.copyFrom(Arrays.copyOfRange(fileContent, MAX_BLOCK_SIZE * idx, MAX_BLOCK_SIZE * idx + offset)))
                        .setFilename(filename)
                        .build());
                idx++;
            }
        }
    }

    private Timestamp getDate(String date) {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = formatter1.parse(date);
            return Timestamp.of(date1);
        } catch (Exception e) {
            throw new IllegalArgumentException("\"" + date + "\" is not a valid " + formatter1.toPattern() + " date!");
        }
    }

    public byte[] downloadDocument(String bucketName, String blobName) {
        try {
            StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(KeyCredential.getGoogleCredentialsInstance()).build();
            Storage storage = storageOptions.getService();
            return storage.readAllBytes(BlobId.of(bucketName, blobName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
