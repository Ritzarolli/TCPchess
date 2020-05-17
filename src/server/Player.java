
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Serves as a client with which to create new threads
 * @author mnhammond0
 */
public class Player implements Runnable {
   
        Socket playerSocket;
        BufferedReader in;
        PrintWriter out;

        /**
         * Constructor
         * @param playerSocket
         * @throws IOException 
         */
        public Player(Socket playerSocket) throws IOException {
            this.playerSocket = playerSocket;
        }
        
        /**
         * Return the socket of the client requested
         * @param user
         * @return 
         */
        public Socket returnSocket(Player user){
            return user.playerSocket;
        }

        /**
         * Communicates with each thread 
         * to process input to the server
         */
        @Override
        public void run() {
            try {

                in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream() ));
                out = new PrintWriter(playerSocket.getOutputStream(), true);
                
                out.println("W E L C O M E \n\nType \"X\" to quit anytime.");
                
                while (true) {
                    String request = in.readLine();
                    if (request.equalsIgnoreCase("X")){
                        out.println("Exiting session. Socket closing.");
                        try {
                            playerSocket.close();
                            break;
                        } catch (IOException ioe) {
                            out.println("Error closing socket.");
                        }
                    } else {
                        out.println("Invalid command.");
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
