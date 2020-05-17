
package client;

import java.net.*;
import java.io.*;
/**
 * Sends client commands to the server on the local machine.
 * Uses Communication class to receive server responses for individual threads
 * 
 * @author mnhammond0
 * @version 2020.05.16
 */

public class Client {
        private static PrintWriter output;
        private static Socket socket;
        private static final int SERVER_PORT = 9090;
        private static final String SERVER_IP = "127.0.0.1";

    
    public static void main(String[] args) throws IOException {
        try {
        socket = new Socket(SERVER_IP,SERVER_PORT);
        
        Communication serverComm = new Communication(socket);  
        
        DataInputStream keyboard = new DataInputStream(System.in);
        output = new PrintWriter(socket.getOutputStream(), true);
        
        new Thread(serverComm).start();  //gives each client its own server input stream
                
        while (true) {    
            String command = keyboard.readLine();
            if (command.equalsIgnoreCase("X")) break;
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
