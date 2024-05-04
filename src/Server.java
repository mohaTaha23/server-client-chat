import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket socket;
        Socket cs;
        try {
            socket = new ServerSocket(12345);
            System.out.println("server is now listening on 12345...");
            cs = socket.accept();
            DataInputStream in = new DataInputStream(new BufferedInputStream(cs.getInputStream()));
            while (cs.isConnected()){
                String massage = in.readUTF();
                System.out.println(massage);
            }
            socket.close();
            in.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
