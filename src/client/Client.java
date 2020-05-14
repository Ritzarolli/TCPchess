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
        //private static int serverPort;
        //private static Scanner input;
        private static PrintWriter output;
        private static Socket socket;
        private static final int SERVER_PORT = 9090;
        private static final String SERVER_IP = "127.0.0.1";
        public static String playerName;

    
    public static void main(String[] args) throws IOException {
        try {
        socket = new Socket(SERVER_IP,SERVER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        output = new PrintWriter(socket.getOutputStream(), true);
        
        while (true) {
            System.out.println("Welcome! Type \"Y\" to begin or \"N\" to exit: ");
            
            String command = keyboard.readLine();
            
            if (command.equals("N")){
                break;
            } else if (command.equals("Y")){
                output.println(command);
                String serverResponse = input.readLine();
                System.out.println(serverResponse);
            }
        }
        } catch (Exception e){
            System.out.println("Error in Client main");
            e.printStackTrace();
        }        
    }

}

