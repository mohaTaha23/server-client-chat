import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",12345);
            String outout = "hiFromTheClient";
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String message ;
            Scanner soso = new Scanner(System.in);
            message = soso.nextLine();
            while (! message.equals("stop")){
                out.writeUTF(message);
                message = soso.nextLine();
            }
            socket.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
