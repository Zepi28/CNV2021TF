package storage;

import contract.ResultUpload;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;

public class UploadBlobStreamObserver implements StreamObserver<ResultUpload> {
    private boolean isFinished = false;
    private boolean isSuccess = false;
    private String filename = null;
    private final LinkedList<String> results = new LinkedList<>();

    public LinkedList<String> getResults() {
        return results;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void onNext(ResultUpload result) {
        results.add(result.getLabel());
        if (filename == null) {
            filename = result.getFilename();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call: " + throwable.getMessage());
        isFinished = true;
    }

    @Override
    public void onCompleted() {
        System.out.println("Stream completed!");
        isFinished = true;
        isSuccess = true;
    }

    public String getFilename() {
        return filename;
    }
}
