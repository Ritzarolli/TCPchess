
package client;

import java.io.*;
import java.net.Socket;

/**
 * Receives messages from the server for individual clients
 * @author mnhammond0
 */
public class Communication implements Runnable {
    
    private Socket server;
    private BufferedReader in;

    public Communication(Socket s) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream() ));
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String serverResponse = in.readLine();
                if (serverResponse == null) break;
                System.out.println(serverResponse);
            }
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
            in.close();
            } catch (IOException ioe){
                ioe.printStackTrace();
              }
          }
    }
}

