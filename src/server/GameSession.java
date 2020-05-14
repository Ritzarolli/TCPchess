/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import chess.*;
import java.net.*;

/**
 *
 * @author mnhammond0
 */
public class GameSession implements Runnable {
    private Server server;
    private Socket player1;
    private String p1Color;
    private Socket player2;
    private String p2Color;
    private Board board;
    
    public GameSession(Server server, Socket p1s, Socket p2s){
        player1 = p1s;
        player2 = p2s;
        server = this.server;
    }

    @Override
    public void run() {
        
    }
    
}
