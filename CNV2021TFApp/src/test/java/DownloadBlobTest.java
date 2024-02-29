import IPlookup.ListIPs;
import contract.Contents;
import contract.RequestFiles;
import org.junit.Assert;
import org.junit.Test;
import storage.BucketStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class DownloadBlobTest {
    ListIPs listIPs = new ListIPs();
    private final String ip;

    public DownloadBlobTest() {
        ip = listIPs.getIP(null);
    }

    @Test
    public void getipTest() {
        Assert.assertNotNull(ip);
    }

    @Test
    public void uploadAndDownloadBlobTest() throws IOException, InterruptedException {
        //Different name blobs so it doesn't overwrite client's blobs :
        //(could also use KEEP_BOTH flag on uploadBlob , the problem is that we can't delete the blobs
        //so that flag would create 3 different blobs everytime tests were run
        LinkedList<String> uploadFilenameList = new LinkedList<>(Arrays.asList("TEST_IMAGE_flower.jpg", "TEST_IMAGE_tubarao.jpg", "TEST_IMAGE_sardinhas.jpg"));
        LinkedList<String> filesList = new LinkedList<>(Arrays.asList("flower.jpg", "tubarao.jpg", "sardinhas.jpg"));
        LinkedList<String> labels = new LinkedList<>(Arrays.asList("Flor","Água", "Comida"));

        BucketStorage bucketStorage = new BucketStorage(ip,false);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateBeg = dtf.format(LocalDateTime.now());
        String dateEnd = dtf.format(LocalDateTime.now().plusDays(1));

        for (int i = 0; i < uploadFilenameList.size(); i++) {
            String label = labels.get(i);

            String filename = uploadFilenameList.get(i);
            bucketStorage.uploadBlob(Contents.Flags.OVERWRITE,filename);
            Assert.assertTrue(bucketStorage.uploadBlobStreamObserver.isFinished());
            Assert.assertTrue(bucketStorage.uploadBlobStreamObserver.isSuccess());
            LinkedList<String> list = bucketStorage.uploadBlobStreamObserver.getResults();
            boolean containsLabel = false;
            for (String labelScore : list) {
                if (labelScore.contains(label)) {
                    containsLabel = true;
                    break;
                }
            }
            Assert.assertTrue(containsLabel);

            filename = filesList.get(i);
            label = labels.get(i);

            byte[] expectedBytes = Files.readAllBytes(new File("testfiles/" + filename).toPath());

            bucketStorage.downloadBlob(RequestFiles.newBuilder()
                    .setDate1(dateBeg)
                    .setDate2(dateEnd)
                    .setLabel(label)
                    .setDownload(true)
                    .build()
            );

            Assert.assertTrue(bucketStorage.filesStreamObserver.isFinished());
            Assert.assertTrue(bucketStorage.filesStreamObserver.isSuccess());

            HashMap<String, byte[]> map = bucketStorage.filesStreamObserver.getMap();
            byte[] actualBytes = map.get("bucket-cn-trabalho-final/" + uploadFilenameList.get(i));

            Assert.assertArrayEquals(expectedBytes, actualBytes);
        }
    }
}
