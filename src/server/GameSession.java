/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import chess.*;
import java.io.*;
import java.net.*;
import java.util.Random;

/**
 *
 * @author mnhammond0
 */
public class GameSession implements Runnable {
    private Server server;
    private Socket player1;
    private String p1Color;
    private PrintWriter p1out;
    private Socket player2;
    private String p2Color;
    private PrintWriter p2out;
    private Board board;
    
    public GameSession(Server server, Socket p1s, Socket p2s) {
        player1 = p1s;
        player2 = p2s;
        server = this.server;
        
        try {
            p1out = new PrintWriter(player1.getOutputStream());
            p2out = new PrintWriter(player2.getOutputStream());
            
        } catch (IOException ioe){
            
        }
        
        board = new Board();
        
        Random chance = new Random();
        int flipCoin = chance.nextInt();
        if (flipCoin == 1){
            p1Color = "white";
            p2Color = "black";
        } else {
            p1Color = "black";
            p2Color = "white";
        }
    }

    @Override
    public void run() {
        System.out.println("New chess game has begun!");
        board.printBoard();
        
    }
    
}
