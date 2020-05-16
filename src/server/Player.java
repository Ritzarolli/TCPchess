
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author mnhammond0
 */
public class Player implements Runnable {
   
        Socket playerSocket;
        BufferedReader in;
        PrintWriter out;


        public Player(Socket playerSocket) throws IOException {
            this.playerSocket = playerSocket;
        }
        
        public Socket returnSocket(Player user){
            return user.playerSocket;
        }

        @Override
        public void run() {
            try {

                in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream() ));
                out = new PrintWriter(playerSocket.getOutputStream(), true);
                
                out.println("W E L C O M E \n\nType \"EXIT\" to quit anytime.");
                
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
