/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import chess.Board;
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


        public Player(Socket playerSocket) throws IOException {
            this.playerSocket = playerSocket;
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()) );
            out = new PrintWriter(playerSocket.getOutputStream(), true);
        }
        
        /**
        public static String getUsername() throws IOException {
            out.println("Welcome! Please enter a username: ");
            String nameString = in.readLine();
                
            try {
                if (nameString != null && nameString.length() >= 2) {
                    playerName = nameString;
                } else {
                    out.println("Please enter a different username: ");
                    in.readLine();
                }
            } catch (Exception e){
                System.out.println("Error at promptUsername");
            }
            return playerName;
        }
        */
    
        @Override
        public void run() {
            try {
                out.println("Type \"START\" to initiate a game or \"EXIT\" to quit.");
                
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
                        out.println("loOk At ThIs ChEsS GaMe ooOooOOoo"); 
                        Server.sendList();
                    } else {
                        out.println("Invalid command.");
                        out.flush();
                    }
                }
            } catch (IOException ioe) {
                System.err.println("IO Exception in Player class");
                System.err.println(ioe.getStackTrace());
            } finally {
                out.close();
                try {
                    in.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        
}
