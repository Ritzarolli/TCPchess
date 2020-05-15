
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
    private static Socket playerSocket;
    private static Player clientThread;
    private static PrintWriter out;
    private static Scanner in;
    private static final int SERVER_PORT = 9090;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static LinkedList<Player> playerList = new LinkedList<>();


    public static void main(String[] args) throws IOException {
        
        ServerSocket listener = new ServerSocket(SERVER_PORT);
        int threadCount = 1;
        System.out.println("Chess server running ...");        
        
        while(running) {
            playerSocket = listener.accept();
            System.out.println("Client "+threadCount+" connected successfully.");
                
            //out = new PrintWriter(playerSocket.getOutputStream(), true);    //send message TO the client
            //in = new Scanner(playerSocket.getInputStream());

            clientThread = new Player(playerSocket);
            playerList.add(clientThread);
            threadPool.execute(clientThread);
            threadCount++;
            }
    }
    
    public void startGame() {
            Socket p1 = playerList.pop().returnSocket(this.clientThread);
            Socket p2 = playerList.pop().returnSocket(this.clientThread);
                            
            GameSession newGame = new GameSession(this, p1, p2);
            threadPool.equals(newGame);
        }
        
        //client handler
    private static class Player implements Runnable {
   
        static Socket playerSocket;
        static Scanner keyIn;
        static BufferedReader in;
        static PrintWriter out;


        public Player(Socket playerSocket) throws IOException {
            this.playerSocket = playerSocket;
            
        }
        
        public void initiateMatch() {
            
            //startGame();
        }

        @Override
        public void run() {
            try {

                in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream() ));
                out = new PrintWriter(playerSocket.getOutputStream(), true);
                
                out.println("W E L C O M E \n\nType \"START\" to initiate a game or \"EXIT\" to quit.");
                
                while (true) {
                    String request = in.readLine();
                    if (request.equalsIgnoreCase("EXIT")){
                        out.println("Exiting session. Socket closing.");
                        try {
                            playerSocket.close();
                            break;
                        } catch (IOException ioe) {
                            out.println("Error closing socket.");
                        }
                    }
                    else if (request.equalsIgnoreCase("START")) {
                        out.println("loOk At ThIs ChEsS GaMe ooOooOOoo");
                        if (playerList.size()>1){
                            //startGame();
                            
                        }
                        //sendList();
                        //challengeOpponent();
                    } else {
                        out.println("Invalid command.");
                        //out.flush();
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
        
        public Socket returnSocket(Player user){
            Socket temp = this.playerSocket;
            return temp;
        }
}
        
                // send list of opponents to client
        public static void sendList() throws IOException {
            int num = 1;  //to number the players in the list to simplify selection
            if (!playerList.isEmpty()){  //if the list contains more than just the current player
          
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
                     
            } else {
                out.println("There are currently no other players.");
            }
        }
        
        
        // challenge an opponent to play; opponent can accept or reject
        // *********** Should this move to GameSession class??? ************
        public static Player challengeOpponent() throws IOException {
            Player opponent = null;
            while (in.hasNextLine()) {
                String selection = in.nextLine();
                
                for (int i = 0; i < playerList.size(); i++){
                    if (selection.contains(playerList.get(i).toString())){         // if the selection (name typed) matches a playerName in the playerList
                        opponent = (playerList.get(i));
                        //PrintWriter oppOut = new PrintWriter(opponent.playerSocket.getOutputStream());
                        out.println("You have been challenged to a game.");
                        out.println("Do you accept? (Reply Y or N)");
                        
                        //Scanner oppIn = new Scanner(opponent.playerSocket.getInputStream());
                        String response = in.nextLine();
                        while (in.hasNextLine()) {
                            if (response.startsWith("Y")){
                                out.println("Challenge accepted. Game on!");
                                return opponent;
                            } else if (response.startsWith("N")) {
                                out.println("Your request has been rejected.");
                            } else {
                                out.println("The selected player is unreachable.");
                            }
                        }
                    } else {
                        out.println("Please try another number.");
                    }
                }  
            }
            return opponent;
        }
    }
    


    /**
    public class Server {
    private static ArrayList<ThreadHandler> clientList = new ArrayList<>();
    public static void main(String[] args) {
        
        int serverPort = 8190;
        boolean running = true;
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            int threadCount = 1;
            ServerSocket listener = new ServerSocket(serverPort);
            System.out.println("Server listening ...");
            
            while (running) {
                Socket newSocket = listener.accept();
                System.out.println("Client "+threadCount+" connected successfully.");
                
                Runnable client = new ThreadHandler(newSocket);
                Thread thread = new Thread(client);
                thread.start();
                threadCount++;
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            
        }
    }
}


class ThreadHandler implements Runnable {
    private Socket newSocket;
    
    public ThreadHandler(Socket newSocket){
        this.newSocket = newSocket;
    }

    @Override
    public void run() {
        try {
            try {
                DataInputStream in = new DataInputStream(newSocket.getInputStream());
                
                boolean done = false;
                while (!done) {
                    System.out.print("Recieved from: " + newSocket.toString() + " message: " + in.readUTF());
                }
            } finally {
                newSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
}*/