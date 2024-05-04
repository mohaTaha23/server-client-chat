import java.io.*;
import java.net.*;

public class ChatClient {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to the server.");

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String userInput;
                while ((userInput = stdIn.readLine()) != null ) {
                    out.println(userInput);
                }
                stdIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread receiveThread = new Thread(() -> {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Server: " + inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendThread.start();
        receiveThread.start();

        try {
            sendThread.join();
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.close();
        in.close();
        socket.close();
    }
}
