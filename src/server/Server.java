
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

            clientThread = new Player(playerSocket);
            playerList.add(clientThread);
            threadPool.execute(clientThread);
            threadCount++;
            
            for (int i = 0; i < playerList.size(); i++) {
                System.out.println(playerList.get(i).toString());
            }
            out = new PrintWriter(playerSocket.getOutputStream(), true);    //send message TO the client
            in = new Scanner(playerSocket.getInputStream());
            
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
    
        public static void startGame(){
            Player temp1 = clientThread;
            Player temp2 = playerList.pop();
            Socket p1 = temp1.returnSocket(temp1);
            Socket p2 = temp2.returnSocket(temp2);
            GameSession newGame = new GameSession(p1, p2);
            threadPool.execute(newGame);
        }
       
    
        // send list of opponents to client
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
        
        
        // challenge an opponent to play; opponent can accept or reject
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