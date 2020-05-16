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
    
    public GameSession(Socket p1s, Socket p2s) {
        player1 = p1s;
        player2 = p2s;
        
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
    
    //return the board's current state
    public Board getBoardState() {
        return board;
    }
    
    //update the board after a move
    public void setBoardState(Move playerMove) {
        board.move(playerMove);
        board.getBoard();
    }
    
    //check who's turn it is
    public Boolean isWhiteTurn(){
        board.isWhiteTurn();
        return true;
    }
    
    //parse the player's input to get their move
    public Move processInput(Socket player, String move) {
        int fromCol = Integer.parseInt(move.substring(0, 1)); //these are subject to change
        int fromRow = Integer.parseInt(move.substring(2, 3)); //depending on how I prompt user
        int toCol = Integer.parseInt(move.substring(4, 5));
        int toRow = Integer.parseInt(move.substring(6, 7));
        
        Move command = new Move(fromCol, fromRow, toCol, toRow);
        
        return command;
    }
    

    @Override
    public void run() {
        System.out.println("New chess game has begun!");
        p1out.print(board);
        p2out.print(board);
        
        //TODO
        
    }
    
}
