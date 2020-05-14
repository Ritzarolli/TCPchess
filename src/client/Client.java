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
        
        GameConnection game = new GameConnection(socket);
        
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        output = new PrintWriter(socket.getOutputStream(), true);
        
        //System.out.println("Welcome! Type \"Y\" to begin or \"N\" to exit: ");
        
        new Thread(game).start();
                
            while (true) {    
                String command = keyboard.readLine();
                if (command.equalsIgnoreCase("N")) break;
                output.println(command);
            }
        } catch (Exception e){
            System.out.println("Error in Client main");
            e.printStackTrace();
        }        
    }

}

