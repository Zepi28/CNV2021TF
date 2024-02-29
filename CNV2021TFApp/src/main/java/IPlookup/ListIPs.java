package IPlookup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ListIPs {

    private static final String PROJECT_ID = "g08-t3n-v2021";
    private static final String CLOUD_FUNCTION_NAME = "getIPs";
    private final String MAIN_ZONE = "europe-west2";
    private LinkedList<String> ips = new LinkedList<>();
    private String URL = null;

    public ListIPs() {
        setURL();
        setListIPs();
    }

    public void setListIPs() {
        ips = new LinkedList<>();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String[] parts = response.body().split("\n");
                ips.addAll(Arrays.asList(parts));
            } else if (response.statusCode() == 404) {
                System.out.println(response.body());
            } else {
                throw new IOException("ERROR obtaining response from : " + URL);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setURL() {
        this.URL = "https://"
                + MAIN_ZONE + "-"
                + PROJECT_ID + "."
                + "cloudfunctions.net/"
                + CLOUD_FUNCTION_NAME;
    }

    public String pickRandomIP() {
        if (!ips.isEmpty()) return ips.get(new Random().nextInt(ips.size()));
        else return null;
    }

    public String getIP(Scanner scanner) {
        String ip;
        do {
            ip = pickRandomIP();
            if (ip == null) {
                if (scanner == null) {
                    System.out.println("Scanner parameter cannot be null!");
                    return null;
                }
                System.out.print("IP List request has failed , do you want to try again? [y/n] ");
                if (Character.toLowerCase(scanner.nextLine().charAt(0)) == 'y') {
                    setListIPs();
                } else {
                    System.out.println("Aborted program\n.\n.");
                    System.exit(0);
                }
            }
        } while (ip == null);
        return ip;
    }
}
