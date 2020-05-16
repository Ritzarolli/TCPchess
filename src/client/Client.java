
package client;
import java.net.*;
import java.util.*;
import java.io.*;
/**
 * Sends client commands to the server.
 * @author mnhammond0
 */

public class Client {
        private static PrintWriter output;
        private static Socket socket;
        private static final int SERVER_PORT = 9090;
        private static final String SERVER_IP = "127.0.0.1";
        public static String playerName;

    
    public static void main(String[] args) throws IOException {
        try {
        socket = new Socket(SERVER_IP,SERVER_PORT);
        
        Communication serverComm = new Communication(socket);  
        
        DataInputStream keyboard = new DataInputStream(System.in);
        output = new PrintWriter(socket.getOutputStream(), true);
        
        new Thread(serverComm).start();  //gives each client its own server input stream
                
        while (true) {    
            String command = keyboard.readLine();
            if (command.equalsIgnoreCase("Bye")) break;
            output.println(command);
            }
        } catch (Exception e){
            System.out.println("Error in Client main");
            e.printStackTrace();
        }        
        
        socket.close();
        System.exit(0);
    }

}



/**
public class Client {
    private static PrintWriter output;
    private static Socket socket;
    private static final int SERVER_PORT = 8190;
    private static final String SERVER_IP = "127.0.0.1";
    
    public static void main(String[] args) throws IOException {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            out.writeUTF("Please let it work...");
            out.writeChars("Is this better?");
            
            Scanner scan = new Scanner(System.in);
            System.out.println(">  ");
            String message = scan.nextLine();
            
            out.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}*/

