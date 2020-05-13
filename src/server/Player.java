/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author mnhammond0
 */
public class Player implements Runnable {
   
        static Socket playerSocket;
        static String playerName;
        static Scanner keyIn;
        static BufferedReader in;
        static PrintWriter out;


        public Player(Socket playerSocket, String playerName) throws IOException {
            this.playerSocket = playerSocket;
            this.playerName = playerName;
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()) );
            out = new PrintWriter(playerSocket.getOutputStream(), true);
        }
    
        @Override
        public void run() {
            try {
                while (true) {
                    String request = in.readLine();
                    if (request.equalsIgnoreCase("EXIT")){
                        out.println("Exiting session. Socket closing.");
                        try {
                            playerSocket.close();
                        } catch (IOException ioe) {
                            out.println("Error closing socket.");
                        }
                    }
                    else if (request.equalsIgnoreCase("START")) {
                        //Server.sendList();
                    } else {
                        out.println("Type \"START\" to initiate a game or \"EXIT\" to quit.");
                        out.flush();
                    }
                }
            } catch (IOException ioe) {
                System.err.println("IO Exception in Player class");
                System.err.println(ioe.getStackTrace());
            }
        }
        
        public void setup() throws IOException {
            playerSocket = this.playerSocket; 
            playerName = this.playerName;
            }
        
        public static String getPlayerName() throws IOException {
            keyIn = new Scanner(playerSocket.getInputStream());             //get message FROM the client
            out = new PrintWriter(playerSocket.getOutputStream(), true);
                String nameString = keyIn.nextLine();
                if (nameString == null || nameString.length()<3) {
                    out.println("Please enter a different username: ");
                    keyIn.nextLine();
                } else {
                    playerName = nameString;
            }
            return playerName;
        }
        

}
