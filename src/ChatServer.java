import java.io.*;
import java.net.*;

public class ChatServer {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for a client...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
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
                    System.out.println("Client: " + inputLine);
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
        clientSocket.close();
        serverSocket.close();
    }
}
