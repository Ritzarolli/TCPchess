
package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A TCP server that creates a new thread for every client that binds.
 * An unlimited amount of threads are executed by the thread pool
 * and the threads are stored in a LinkedList.
 * 
 * The server also handles all game data for a chess game between two clients.
 * A new game is started once two clients connect.
 * 
 * @author mnhammond0
 */

public class Server {
    private static boolean running = true;
    private static final int SERVER_PORT = 9090;
    private static Socket playerSocket;
    private static Player clientThread;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static LinkedList<Player> playerList = new LinkedList<>();
    private static PrintWriter out;
    private static Scanner in;


    public static void main(String[] args) throws IOException {
        
        ServerSocket listener = new ServerSocket(SERVER_PORT);
        int threadCount = 1;
        System.out.println("Chess server running ...");        
        
        while(running) {
            playerSocket = listener.accept();
            System.out.println("Client "+threadCount+" connected successfully.");

            clientThread = new Player(playerSocket);
            playerList.add(clientThread);
            threadPool.execute(clientThread);
            threadCount++;
            
            for (int i = 0; i < playerList.size(); i++) {
                System.out.println(playerList.get(i).toString());
            }
            out = new PrintWriter(playerSocket.getOutputStream(), true);    //send message TO the client
            in = new Scanner(playerSocket.getInputStream());                //gets message FROM the client
            
            if (playerList.size()>1){
                Player temp1 = playerList.pop();
                Player temp2 = playerList.pop();
                Socket p1 = temp1.returnSocket(temp1);
                Socket p2 = temp2.returnSocket(temp2);
                GameSession newGame = new GameSession(p1, p2);
                threadPool.execute(newGame);
            }
        }
    }
      
    /**
     * Sends a list of players who are connected and waiting to play a game
     * @throws IOException 
     */
    public static void sendList() throws IOException {
        int num = 1;  //to number the players in the list to simplify selection
        if (playerList.size()>1){  //if the list contains more than just the current player
          
            ArrayList<String> opponents = new ArrayList();
            for (Player user : playerList){
                for (int i = 0; i < playerList.size(); i++) {
                    if (!user.equals(playerList.get(i))){
                    opponents.add(playerList.get(i).toString());
                    }   
                }
            }
                for (String opponent : opponents) {
                    out.println("[ "+num+" ] "+opponent);
                    num=num+1;
                }
                out.println("Select an opponent from the list above \nby typing their number:");
                if (in.hasNextLine()) {
                    String selection = in.nextLine(); //player's number gets input as String
                    int playerNum = Integer.parseInt(selection); //parse that String into an int
                    challengeOpponent(playerNum);
                }
                     
            } else {
                out.println("There are currently no other players.");
            }
        }

    /**
     * Challenge an opponent to play; opponent can accept or reject
     * @param selection
     * @return
     * @throws IOException 
     */
    public static Player challengeOpponent(int selection) throws IOException {
        Player opponent = null;
        while (in.hasNextLine()) {
                
            for (int i = 0; i < playerList.size(); i++){
                if (selection == i+1){         // a player's number is +1 higher than its index
                    opponent = (playerList.get(i));

                    out.println("You have been challenged to a game.");
                    out.println("Do you accept? (Reply Y or N)");
                        
                    String response = in.nextLine();
                    while (in.hasNextLine()) {
                        if (response.equalsIgnoreCase("Y")){
                            out.println("Challenge accepted. Game on!");
                            return opponent;
                        } else if (response.equalsIgnoreCase("N")) {
                            out.println("Your request has been rejected.");
                        }
                    }
                } else {
                    out.println("The selected player is unreachable.");
                    out.println("Please try another number.");
                    sendList();
                }
            }  
        } return opponent;
    }
}