package storage;

import contract.ResultFiles;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.LinkedList;

public class FilesStreamObserver implements StreamObserver<ResultFiles> {
    HashMap<String, byte[]> pairMap = new HashMap<>();
    private LinkedList<String> filenameResults = new LinkedList<>();
    private boolean isFinished = false;
    private boolean isSuccess = false;
    boolean isDownload;

    public FilesStreamObserver(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public HashMap<String, byte[]> getMap() {
        return pairMap;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public void onNext(ResultFiles resultReceive) {
        if (isDownload) {
            String filename = resultReceive.getFilename();
            byte[] bytes = resultReceive.getData().toByteArray();
            if (pairMap.containsKey(filename)) {
                //join bytes[]
                byte[] arr = pairMap.get(filename);
                pairMap.put(filename, joinArrays(arr, bytes));
            } else {
                pairMap.put(filename, bytes);
            }
        } else {
            filenameResults.add(resultReceive.getFilename());
        }
    }

    public byte[] joinArrays(byte[] original, byte[] toAdd) throws IllegalArgumentException {
        byte[] newArr = new byte[original.length + toAdd.length];
        System.arraycopy(original, 0, newArr, 0, original.length);
        System.arraycopy(toAdd, 0, newArr, original.length, toAdd.length);
        return newArr;
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call: " + throwable.getMessage());
        isFinished = true;
    }

    @Override
    public void onCompleted() {
        isFinished = true;
        isSuccess = true;
    }

    public LinkedList<String> getFilenamesList() {
        return filenameResults;
    }
}