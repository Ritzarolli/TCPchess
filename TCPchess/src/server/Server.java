/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
//import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mnhammond0
 */
public class Server {
    private static boolean running = true;
    //private static Socket playerSocket; 
    private static final int SERVER_PORT = 9090;
    private static ExecutorService threadPool;
    private static LinkedList<Player> playerList;
    private static Player player1; //**Need to move this to separate GameSession class
    //private static Player player2; ** " 
    
    //private ObjectOutputStream out;

    public static void main(String[] args) throws IOException {

        try (ServerSocket listener = new ServerSocket(SERVER_PORT)){
            System.out.println("Chess server running ...");
            threadPool = Executors.newCachedThreadPool();
        
            while(running) {
                threadPool.execute( new Player(listener.accept()) );
            }
        }
    
    /**
    client.close();
    listener.close();
    * */
    }



    static class Player implements Runnable {
        Socket playerSocket;
        String playerName;
        Scanner keyIn;
        PrintWriter out;
        
        public Player(Socket playerSocket){
            this.playerSocket = playerSocket;
        }
        
        //Overload
        public Player(Socket playerSocket, String playerName){
            this.playerSocket = playerSocket;
            this.playerName = playerName;
        }
    
        @Override
        public void run() {
            try {
                setup();
            } catch (IOException ioe) {
            } finally {
                try {
                    playerSocket.close();
                } catch (IOException ioe){                    
                }
            }
        }
        
        public Player setup() throws IOException {
            out = new PrintWriter(playerSocket.getOutputStream(), true);    //send message TO the client
            out.println("Welcome! Please enter a username: ");
            out.println("Type \"START\" to initiate a game or \"EXIT\" to quit.");
            keyIn.nextLine();
            processInput();
            return player1;
            }
        
        
        public String getPlayerName() throws IOException {
            keyIn = new Scanner(playerSocket.getInputStream());             //get message FROM the client
            while (keyIn.hasNextLine()){
                String nameString = keyIn.nextLine();
                if (nameString == null || nameString.length()<3) {
                    out.println("Please enter a different username: ");
                    keyIn.nextLine();
                } else {
                    playerName = keyIn.nextLine();
                }
            }
            return playerName;
        }
        
        public void processInput() throws IOException {
            while (keyIn.hasNextLine()){
                String input = keyIn.nextLine();
                if (input.startsWith("EXIT")){
                    out.println("Exiting session. Socket closing.");
                    try {
                        playerSocket.close();
                    } catch (IOException e) {
                    }
                } else if (input.startsWith("START")){
                    sendList();
                } else {
                    out.println("Type \"START\" to initiate a game or \"EXIT\" to quit.");
                    out.flush();
                }
            }
        }
        
        // challenge an opponent to play; opponent can accept or reject
        // *********** Should this move to GameSession class??? ************
        public Player challengeOpponent() throws IOException {
            Player opponent = null;
            while (keyIn.hasNextLine()) {
                String selection = keyIn.nextLine();
                
                for (int i = 0; i < playerList.size(); i++){
                    if (selection.contains(playerList.toString())){         // if the selection (name typed) matches a playerName in the playerList
                        opponent = (playerList.get(i));
                        opponent.out.println("You have been challenged to a game.");
                        opponent.out.println("Do you accept? (Reply Y or N)");
                        
                        String response = opponent.keyIn.nextLine();
                        while (opponent.keyIn.hasNextLine()) {
                            if (response.startsWith("Y")){
                                return opponent;
                            } else if (response.startsWith("N")) {
                                out.println("Your request has been rejected.");
                            } else {
                                out.println("The selected player is unreachable.");
                            }
                        }
                    } else {
                        out.println("Please try another name.");
                    }
                }  
            }
            return opponent;
        }
        
        // send list of opponents to client
        public void sendList() throws IOException {
            
            if (!playerList.isEmpty()){
          
                for (Player users : playerList){
                    LinkedList<String> opponents = new LinkedList();
                    for (int i = 0; i < playerList.size(); i++) {
                        opponents.add(playerList.get(i).playerName);
                    }
                    out.println("Select an opponent from below by typing their name:");
                    for (String opponent : opponents) {
                        out.println(opponents);
                    }
                } 
            } else {
                out.println("There are currently no other players.");
            }
        }
    
    }
}
