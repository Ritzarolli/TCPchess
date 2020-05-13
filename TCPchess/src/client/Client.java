/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.net.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author mnhammond0
 */
public class Client {
        private static int serverPort;
        private static Scanner input;
        private static Socket socket;
        private static final int SERVER_PORT = 9090;
        private static final String SERVER_IP = "127.0.0.1";
        
    /**    
        public Client(){
            serverPort = 9090;
            socket = new Socket();
            input = new Scanner(System.in);
    }
    */
    
    public static void main(String[] args) throws IOException {
        serverPort = 9090;
        socket = new Socket("127.0.0.1",serverPort);
        input = new Scanner( new InputStreamReader(socket.getInputStream()) );
        
        while (input.hasNextLine()){
            String serverResponse = input.nextLine();
            System.out.println(serverResponse);
        }
        socket.close();
}
        
    public void run() throws IOException {
        //System.out.print("Enter server IP: ");
        //String ip = input.nextLine();
        String ip = "127.0.0.1";
        InetSocketAddress sa = new InetSocketAddress(ip, serverPort);
        try {
            socket.connect(sa);
        } catch (SocketException se) {
            socket.close();
        }
        
/**        
    }
    
    private void close() {
        boolean closing = true;
        try {
            System.out.println("There was an error connecting to the server. Please check the IP Address and try again.");
            socket.close();
        } catch (IOException ioe) {
            System.err.println("The socket could not be closed.");
            closing = false;
        }
        if (closing) {
            System.exit(0);
        }
    }*/
}
}
