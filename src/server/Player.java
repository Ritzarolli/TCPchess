/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author mnhammond0
 */
public class Player implements Runnable {
   
        Socket playerSocket;
        String playerName;
        Scanner keyIn;
        PrintWriter out;


        public Player(Socket playerSocket, String playerName){
            this.playerSocket = playerSocket;
            this.playerName = playerName;
        }
    
        @Override
        public void run() {
            try {
                setup();
            } catch (IOException ioe) {
            }
        }
        
        public void setup() throws IOException {
            playerSocket = this.playerSocket; 
            playerName = getPlayerName();
            }
        
        public String getPlayerName() throws IOException {
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
