import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FirebaseServer {

    public static void main(String[] args) throws IOException {
        System.out.println("please enter the message: ");

        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        sendMessage(message);
    }

    private static void sendMessage(String message) throws IOException {
        String url = "https://fcm.googleapis.com/fcm/send";
        URL object=new URL(url);

        HttpURLConnection firebaseConnection = (HttpURLConnection) object.openConnection();
        firebaseConnection.setDoOutput(true);
        firebaseConnection.setDoInput(true);
        firebaseConnection.setRequestProperty("Content-Type", "application/json");
        firebaseConnection.setRequestProperty("Authorization", "key=AAAAq9wwUB0:APA91bG6WyEOHRD6eTWbKFKb5h0lcOfRrcg9brFZzKy-hc0b-F2VK3ghSq82e_taRTjJceYbvhfAz6Z6VfvC-YzlwntxxnGYALi8O2tQs93VtdbNrizTKchG4FDy1oIhqcFaa_OxDsYzanidIBLDx5wRbUO1ZXrSiw");
        firebaseConnection.setRequestMethod("POST");

        //message specific topic
        String jsonString = "{\"to\": \"/topics/all\",\"data\": {\"message\": \""+message+"\"},\"notification\":{\"body\":\""+message+"\"}}";

        //message specific user
        String userId = "d9HRqijs7CM:APA91bFSuTb1cFfIemxCbkG0Y1Bry7qVPAew241MY6OaekJKUtyGMvXIgOwYnLpkHJ7dB9Lnt2PpacAAmTNDIOQCW7qgu6WZfERQt9Ltb4W1KP0c9_wp1G2JmZmtYzGTWYDdRpp9LxVo";
//        String jsonString = "{\"registration_ids\": [\""+userId+"\"],\"data\": {\"message\": \""+message+"\"},\"notification\":{\"body\":\""+message+"\"}}";

        OutputStreamWriter wr = new OutputStreamWriter(firebaseConnection.getOutputStream());
        wr.write(jsonString);
        wr.flush();

        //Display server response

        System.out.println("Response: ");
        StringBuilder sb = new StringBuilder();
        int HttpResult = firebaseConnection.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(firebaseConnection.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(firebaseConnection.getResponseMessage());
        }
    }
}
