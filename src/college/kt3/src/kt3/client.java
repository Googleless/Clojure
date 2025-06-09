import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class client {
    private static final String SERVER_URL = "http://localhost:3000/";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMenu();
    }

    private static void initMenu() {
        while (true) {
            System.out.println("\nURL Shortener Menu:");
            System.out.println("1. Create short URL");
            System.out.println("2. Show normal URL");
            System.out.println("3. Update URL mapping");
            System.out.println("4. Delete short URL");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createShortUrl();
                    break;
                case 2:
                    showNormalUrl();
                    break;
                case 3:
                    updateUrl();
                    break;
                case 4:
                    deleteUrl();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createShortUrl() {
        System.out.print("Enter normal URL to shorten: ");
        String normalUrl = scanner.nextLine();

        try {
            String response = sendPostRequest(SERVER_URL, "{\"url\":\"" + normalUrl + "\"}");
            System.out.println("Short URL created: " + response);
        } catch (Exception e) {
            System.out.println("Error creating short URL: " + e.getMessage());
        }
    }

    private static void showNormalUrl() {
        System.out.print("Enter short URL: ");
        String shortUrl = scanner.nextLine();

        try {
            String response = sendGetRequest(SERVER_URL + shortUrl);
            System.out.println("Normal URL: " + response);
        } catch (Exception e) {
            System.out.println("Error retrieving URL: " + e.getMessage());
        }
    }

    private static void updateUrl() {
        System.out.print("Enter short URL to update: ");
        String shortUrl = scanner.nextLine();
        System.out.print("Enter new normal URL: ");
        String newNormalUrl = scanner.nextLine();

        try {
            int status = sendPutRequest(SERVER_URL + shortUrl, "{\"url\":\"" + newNormalUrl + "\"}");
            if (status == 200) {
                System.out.println("URL mapping updated successfully");
            } else {
                System.out.println("URL not found or update failed");
            }
        } catch (Exception e) {
            System.out.println("Error updating URL: " + e.getMessage());
        }
    }

    private static void deleteUrl() {
        System.out.print("Enter short URL to delete: ");
        String shortUrl = scanner.nextLine();

        try {
            int status = sendDeleteRequest(SERVER_URL + shortUrl);
            if (status == 200) {
                System.out.println("URL deleted successfully");
            } else {
                System.out.println("URL not found or delete failed");
            }
        } catch (Exception e) {
            System.out.println("Error deleting URL: " + e.getMessage());
        }
    }

    private static String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP GET request failed: " + conn.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static String sendPostRequest(String urlString, String body) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        conn.getOutputStream().write(body.getBytes());

        if (conn.getResponseCode() != 201) {
            throw new RuntimeException("HTTP POST request failed: " + conn.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static int sendPutRequest(String urlString, String body) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        conn.getOutputStream().write(body.getBytes());

        return conn.getResponseCode();
    }

    private static int sendDeleteRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        return conn.getResponseCode();
    }
}