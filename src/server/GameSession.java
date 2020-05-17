
package server;

import chess.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A new game matching two connected clients
 * on a server using TCP sockets.
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
    
    /**
     * Constructor for a new game, including adding
     * the player sockets, setting up their input
     * and output streams, and instantiating a
     * new game board object.
     * 
     * A coin flip determines the color for each player
     * @param p1s
     * @param p2s 
     */
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
    
    /**
     * Return the current state of the game board
     * @return 
     */
    public Board getBoardState() {
        return board;
    }
    
    /**
     * Updates the board after a player makes a move
     * @param playerMove 
     */
    public void setBoardState(Move playerMove) {
        board.move(playerMove);
        board.getBoard();
    }
    
    /** /check who's turn it is
    public Boolean isWhiteTurn(){
        if (board.isWhiteTurn()) {
            return true;
        } else return false;
    }*/
    
    /**
     * Parse the player's String input to get the move
     * associated with the player's chosen indeces 
     * @param player
     * @param move
     * @return 
     */
    public Move processInput(Socket player, String move) {
        int fromCol = Character.getNumericValue(move.charAt(0)); //these are subject to change
        int fromRow = Character.getNumericValue(move.charAt(1)); //depending on how I prompt user
        int toCol = Character.getNumericValue(move.charAt(2));
        int toRow = Character.getNumericValue(move.charAt(3));
        
        
        Move command = new Move(fromCol, fromRow, toCol, toRow);
        
        return command;
    }

    /**
     * Controls the game by sending player input to the server
     * and providing the server's response back to the clients
     */
    @Override
    public void run() {
        System.out.println("\nNew chess game has begun!");
        

        p1out.println("\nGAME ON PLAYER 1");
        p1out.println("\nYou are "+p1Color.toUpperCase());
        p2out.println("\nGAME ON PLAYER 2");
        p2out.println("\nYou are "+p2Color.toUpperCase());
        
        while (true) {
            getBoardState();
            boolean whiteTurn = board.isWhiteTurn();  //true
            boolean p1firstMove = true;
            boolean p2firstMove = true;
            
            //first move, white player 1 goes first
            if (p1Color.equalsIgnoreCase("white") && whiteTurn && p1firstMove) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("WHITE moves first.\nMake your move:\n");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("WHITE\n\n");
                p2out.println("Please wait while WHITE makes their move.");
                
                String move = p1in.nextLine();
                setBoardState( processInput(player1, move) );
                whiteTurn=false;
            
            //first move, white player 2 goes first
            } else if (p2Color.equalsIgnoreCase("white") && whiteTurn && p2firstMove) {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("WHITE moves first.\nMake your move:\n");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while WHITE makes their move.");
                
                String move = p2in.nextLine();
                setBoardState( processInput(player2, move) );
                whiteTurn=false;
                
            //white player 1 subsequent moves
            } else if (p1Color.equalsIgnoreCase("white") && whiteTurn && !p1firstMove) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("It is your turn.\nMake your move:\n");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("WHITE\n\n");
                p2out.println("Please wait while WHITE makes their move.");
                
                String move = p1in.nextLine();
                setBoardState( processInput(player1, move) );
                whiteTurn=false;
            
            //white player 2 subsequent moves
            } else if (p2Color.equalsIgnoreCase("white") && whiteTurn && !p2firstMove) {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("It is your turn.\nMake your move:\n");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while WHITE makes their move.");
                
                String move = p2in.nextLine();
                setBoardState( processInput(player2, move) );
                whiteTurn=false;
            
            //black player 1 waits for white to take turn
            } else if (p1Color.equalsIgnoreCase("black") && whiteTurn) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while WHITE makes their move.");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("WHITE\n\n");
                p2out.println("It is your turn.\nMake your move:\n");
                
                String move = p2in.nextLine();
                setBoardState( processInput(player2, move) );
                whiteTurn=false;
            
            //black player 1 move    
            } else if (p1Color.equalsIgnoreCase("black") && !whiteTurn) {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("Please wait while BLACK makes their move.");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("It is your turn.\nMake your move:\n");
                
                String move = p1in.nextLine();
                setBoardState( processInput(player1, move) );
                whiteTurn=true;
                
            //black player 2 waits for white to take turn  
            } else if (p2Color.equalsIgnoreCase("black") && whiteTurn) {
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("Please wait while WHITE makes their move.");
                
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("WHITE\n\n");
                p1out.println("It is your turn.\nMake your move:\n");
                
                String move = p1in.nextLine();
                setBoardState( processInput(player1, move) );
                whiteTurn=false;
            
            //black player 2 move  
            } else if (p2Color.equalsIgnoreCase("black") && !whiteTurn) {
                p1out.println("\nblack");
                p1out.println("\n"+board.boardString());
                p1out.println("\nWHITE\n\n");
                p1out.println("Please wait while BLACK makes their move.");
                
                p2out.println("\nblack");
                p2out.println("\n"+board.boardString());
                p2out.println("\nWHITE\n\n");
                p2out.println("It is your turn.\nMake your move:\n");
                
                String move = p2in.nextLine();
                setBoardState( processInput(player2, move) );
                whiteTurn=true;
            } 
            
        }
        
        
                
        
    }
}
