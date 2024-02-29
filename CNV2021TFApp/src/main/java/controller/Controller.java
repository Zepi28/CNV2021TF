package controller;

import IPlookup.ListIPs;
import contract.RequestFiles;
import storage.BucketStorage;

import java.util.Scanner;

public class Controller {

    private static final Scanner in = new Scanner(System.in);
    private BucketStorage bucketStorage;

    public static void main(String[] args) throws InterruptedException {
        Controller ctrl = new Controller();
        ctrl.run();
    }

    private void run() throws InterruptedException {
        ListIPs listIPs = new ListIPs();
        String ip = listIPs.getIP(in);
        bucketStorage = new BucketStorage(ip);
        System.out.println("Connection established to " + ip);
        String str;
        do {
            showOptions();
            str = in.nextLine().toLowerCase().trim();
            switch (str) {
                case "0":
                    createBlob();
                    break;
                case "1":
                    filesOnDateRange(false);
                    break;
                case "2":
                    filesOnDateRange(true);
                    break;
                default:
                    System.out.println("No command for " + str);
            }
        } while (!str.equalsIgnoreCase("e"));
    }

    private void showOptions() {
        System.out.println("0-Create Blobs");
        System.out.println("1-List Files inside a Date Range");
        System.out.println("2-Download Files inside a Date Range");
        System.out.println("e-Exit");
        System.out.print(">");
    }

    private void createBlob() {
        bucketStorage.createBlob();
    }

    private void filesOnDateRange(boolean toDownload) throws InterruptedException {
        String[] dates = getRangeDate();
        System.out.print("Label ? ");
        String label = in.nextLine();
        RequestFiles requestDownload = RequestFiles.newBuilder()
                .setDate1(dates[0])
                .setDate2(dates[1])
                .setLabel(label)
                .setDownload(toDownload)
                .build();
        bucketStorage.downloadBlob(requestDownload);
    }

    private String[] getRangeDate() {
        String[] dates = new String[2];
        System.out.println("Date 1 : ");
        dates[0] = in.nextLine();
        System.out.println("Date 2 : ");
        dates[1] = in.nextLine();
        return dates;
    }
}
