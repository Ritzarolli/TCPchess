/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author mnhammond0
 */
public class GameConnection implements Runnable {
    
    private Socket server;
    private BufferedReader in;

    public GameConnection(Socket s) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()) );
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

