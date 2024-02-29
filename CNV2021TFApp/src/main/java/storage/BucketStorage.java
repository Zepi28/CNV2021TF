package storage;

import com.google.protobuf.ByteString;
import contract.Contents;
import contract.FileServiceGrpc;
import contract.RequestFiles;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class BucketStorage {

    private static final int svcPort = 7000;
    private final static int MAX_BLOCK_SIZE = 1024;
    private final static Scanner in = new Scanner(System.in);
    private final static String DOWNLOADS_PATH = "Downloads/";
    private final ManagedChannel channel;
    private final FileServiceGrpc.FileServiceStub noBlockStub;
    /**
     * Overrides download command because for some tests, downloading the files is not a requirement
     * and it becomes a hassle in terms of computer performance doing the tests
     */
    private boolean preventDownloadForTesting = false;
    /**
     * Saves the used stream instance(s) for testing the results
     */
    public FilesStreamObserver filesStreamObserver;
    public UploadBlobStreamObserver uploadBlobStreamObserver;

    public BucketStorage(String ip) {
        this.channel = ManagedChannelBuilder.forAddress(ip, svcPort)
                .usePlaintext()
                .build();
        this.noBlockStub = FileServiceGrpc.newStub(channel);
    }

    public BucketStorage(String ip, boolean preventDownloadForTesting) {
        this.channel = ManagedChannelBuilder.forAddress(ip, svcPort)
                .usePlaintext()
                .build();
        this.noBlockStub = FileServiceGrpc.newStub(channel);
        this.preventDownloadForTesting = preventDownloadForTesting;
    }

    public static void main(String[] args) throws InterruptedException {
        BucketStorage bucketStorage = new BucketStorage("localhost");

        RequestFiles requestDownload = RequestFiles.newBuilder()
                .setDate1("17/06/2021")
                .setDate2("22/06/2021")
                .setLabel("Comida")
                .setDownload(false)
                .build();

        bucketStorage.downloadBlob(requestDownload);
        //bucketStorage.createBlob();
    }

    private void showCommands() {
        System.out.println("================== How to Use: ==================");
        System.out.println("1. filename");
        System.out.println("2. filename|flag");
        System.out.println("3. flag|filename\n");
        System.out.println("Available Flags:");
        System.out.println("f - override");
        System.out.println("kb - Keep Both\n");
    }

    /**
     * Returns FilesStreamObserver wiht only usage on only for JUNIT tests
     */
    public void downloadBlob(RequestFiles requestDownload) throws InterruptedException {
        FilesStreamObserver resultStream = new FilesStreamObserver(requestDownload.getDownload());

        noBlockStub.receiveFilesWithLabel(requestDownload, resultStream);

        System.out.println("Active and waiting for Case2 completed ...");
        while (!resultStream.isFinished()) Thread.sleep(1000);
        if (resultStream.isSuccess()) {
            if (requestDownload.getDownload() && !preventDownloadForTesting) {
                try {
                    System.out.println("DOWNLOADING FILES:");
                    downloadAllDocuments(resultStream.getMap());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                System.out.println("Found files :");
                for (String str: resultStream.getFilenamesList()) {
                    System.out.println(str);
                }
            }
        }
        this.filesStreamObserver = resultStream;
    }

    public void createBlob() {
        System.out.print("Insert [Filename] (or 'e' to exit , or 'h' to show flag help) : ");
        String cmd = in.nextLine().trim();

        while (cmd.equalsIgnoreCase("h") || cmd.equalsIgnoreCase("help")) {
            showCommands();
            System.out.print("Insert [Filename] (or 'e' to exit  or 'h' for help) : ");
            cmd = in.nextLine().trim();
        }

        if (cmd.equalsIgnoreCase("e") || cmd.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            return;
        }
        String[] cmdSplitted = cmd.split("\\|");

        Contents.Flags flag = Contents.Flags.NONE;
        String filename = "";
        if (cmdSplitted.length == 1) {
            filename = cmdSplitted[0];
        } else if (cmdSplitted.length == 2) {
            flag = getFlags(cmdSplitted[1]);
            // checks for Filename|Flag
            if (flag != null) {
                filename = cmdSplitted[0];
            } else {
                flag = getFlags(cmdSplitted[0]);
                // checks for Flag|Filename
                if (flag != null) {
                    filename = cmdSplitted[1];
                }
            }
            if (flag == null) {
                System.out.print("Do you want to continue the insertion of blob without flags? [y/n] ");
                if (!in.nextLine().equalsIgnoreCase("y")) {
                    return;
                } else {
                    flag = Contents.Flags.NONE;
                    filename = cmdSplitted[0];
                }
            }
        } else if (cmdSplitted.length > 2) {
            System.out.println("Too many arguments!");
            return;
        }
        uploadBlob(flag, filename);
    }

    public void uploadBlob(Contents.Flags flag, String filename) {
        try {
            // Obter um array de bytes do ficheiro
            File file = new File(filename);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String contentType;
            int indexOfExtension;
            if ((indexOfExtension = filename.indexOf(".")) != -1) {
                contentType = filename.substring(indexOfExtension + 1);
            } else contentType = "UnknownFile";
            // Envia para o Server blocos do file
            UploadBlobStreamObserver resultStream = new UploadBlobStreamObserver();
            StreamObserver<Contents> result = noBlockStub.uploadFileToStorage(resultStream);
            //envia bloco a bloco (1024 a 1024)
            sendFile(flag, filename, fileContent, contentType, result);
            result.onCompleted();

            while (!resultStream.isFinished()) {
                Thread.sleep(1000);
            }

            if (resultStream.isSuccess()) {
                LinkedList<String> results = resultStream.getResults();
                for (String str : results) {
                    System.out.println(str);
                }
                System.out.println("---\nTOTAL NUM OF LABELS : " + results.size());
            }
            this.uploadBlobStreamObserver = resultStream;
        } catch (Exception e) {
            System.out.println("Could not open file : " + filename);
        }
    }

    private void sendFile(Contents.Flags flag, String filename, byte[] fileContent, String contentType, StreamObserver<Contents> result) {
        if (MAX_BLOCK_SIZE >= fileContent.length) {
            sendBlock(filename, flag, fileContent, contentType, result);
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
                sendBlock(filename, flag, Arrays.copyOfRange(fileContent, MAX_BLOCK_SIZE * idx, MAX_BLOCK_SIZE * idx + offset), contentType, result);
                idx++;
            }
        }
    }

    private static Contents.Flags getFlags(String s) {
        switch (s) {
            case "f":
                return Contents.Flags.OVERWRITE;
            case "kb":
                return Contents.Flags.KEEP_BOTH;
            default:
                System.out.println("Invalid flag!");
                return null;
        }
    }

    private static void sendBlock(String filename, Contents.Flags flag, byte[] fileContent, String contentType, StreamObserver<Contents> result) {
        result.onNext(Contents.newBuilder()
                .setFlag(flag)
                .setFileBlockBytes(ByteString.copyFrom(fileContent))
                .setFilename(filename)
                .setContentType(contentType).build());
    }

    private void downloadAllDocuments(HashMap<String, byte[]> map) {
        for (String filename : map.keySet()) {
            byte[] arr = map.get(filename);
            String[] parts = filename.split("/");
            String bucketName = parts[0];
            String blobName = parts[1];
            if (!createDirOnDownloadsFolder(bucketName)) {
                System.out.println("Cannot create Folder(s) for downloads!");
                return;
            }
            try (FileOutputStream stream = new FileOutputStream(DOWNLOADS_PATH + filename)) {
                stream.write(arr);
                stream.flush();
                //TODO o download só completa depois do programa terminar
                System.out.println(
                        "Downloaded \"" + blobName + "\""
                                + " from bucket \"" + bucketName + "\""
                                + " to path "
                                + filename);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean createDirOnDownloadsFolder(String folderName) {
        File directory = new File(DOWNLOADS_PATH + folderName);
        if (!directory.exists()) {
            return directory.mkdirs();
        } else {
            return true;
        }
    }
}