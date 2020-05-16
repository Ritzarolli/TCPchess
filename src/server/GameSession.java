/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import chess.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author mnhammond0
 */
public class GameSession implements Runnable {
    private Socket player1;
    private String p1Color;
    private PrintWriter p1out;
    private Scanner p1in;
    private Socket player2;
    private String p2Color;
    private PrintWriter p2out;
    private Scanner p2in;
    private Board board;
    
    public GameSession(Socket p1s, Socket p2s) {
        player1 = p1s;
        player2 = p2s;
        
        try {
            p1out = new PrintWriter(player1.getOutputStream(), true);  //sends server message to client 1
            p1in = new Scanner(player1.getInputStream()); //send client 1's input to the server
            p2out = new PrintWriter(player2.getOutputStream(), true);  //sends server message to client 2
            p2in = new Scanner(player2.getInputStream()); //send client 2's input to the server

        } catch (IOException ioe){
            
        }
        
        board = new Board();
        
        Random chance = new Random();
        int flipCoin = chance.nextInt(20);
        if (flipCoin == 5){
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
        if (board.isWhiteTurn()) {
            return true;
        } else return false;
    }
    
    //parse the player's input to get their move
    public Move processInput(Socket player, String move) {
        int fromCol = Integer.parseInt(move.substring(0, 1)); //these are subject to change
        int fromRow = Integer.parseInt(move.substring(2, 3)); //depending on how I prompt user
        int toCol = Integer.parseInt(move.substring(5, 6));
        int toRow = Integer.parseInt(move.substring(7, 8));
        
        Move command = new Move(fromCol, fromRow, toCol, toRow);
        
        return command;
    }

    @Override
    public void run() {
        System.out.println("\nNew chess game has begun!");
        

        p1out.println("\nGAME ON PLAYER 1");
        p1out.println("\nYou are "+p1Color.toUpperCase());
        p2out.println("\nGAME ON PLAYER 2");
        p2out.println("\nYou are "+p2Color.toUpperCase());
        
        while (true) {
            getBoardState();
            boolean moved = true;
            
            //first move
            if (p1Color.equalsIgnoreCase("white") && isWhiteTurn() != moved) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("WHITE moves first.\nMake your move:\n");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("WHITE\n\n");
                p2out.println("Please wait while WHITE makes their move.");
                
                String move = p1in.next();
                setBoardState( processInput(player1, move) );
                
            } else {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("WHITE moves first.\nMake your move:\n");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while WHITE makes their move.");
                
                String move = p2in.next();
                setBoardState( processInput(player2, move) );
            }
            
            //subsequent moves
            if (p1Color.equalsIgnoreCase("white") && isWhiteTurn() == moved) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while BLACK makes their move.");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("WHITE\n\n");
                p2out.println("It is your turn.\nMake your move:\n");
                
                String move = p1in.next();
                setBoardState( processInput(player1, move) );
                                   
            } else {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("Please wait while BLACK makes their move.");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("It is your turn.\nMake your move:\n");
                
                String move = p2in.next();
                setBoardState( processInput(player2, move) );
            } 
            
        }
        
        
                
        
    }
}
