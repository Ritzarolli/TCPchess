/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.*;
/**
 * Creates a new chess board with all 32 appropriate pieces.
 * Allows pieces to move on the board and track the current state of the board.
 * @author mnhammond0
 */
public class Board {
    
    private final Piece[][] BOARD;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;
    private boolean isWhiteTurn;
    
    /**
     * Constructor for a new game board,
     * including arrays to contain all the pieces
     */
    public Board() {
        BOARD = new Piece[8][8];
        whitePieces = new ArrayList<>(16);
        blackPieces = new ArrayList<>(16);
        isWhiteTurn = true;
        setStartPieces();
    }
    
    /**
     * Returns the board object
     * @return 
     */
    public Piece[][] getBoard() {
        return BOARD;
    }
    
    /**
     * Returns the piece located in the spot
     * at the given indeces
     * @param col
     * @param row
     * @return 
     */
    public Piece getPiece(int col, int row) {
        return BOARD[col][row];
    }
    
    /**
     * Sets individual pieces at the given indeces
     * @param piece
     * @param col
     * @param row 
     */
    public void setPiece(Piece piece, int col, int row) {
        BOARD[col][row] = piece;
    }
    
    /**
     * For use in game play to determine whose turn it is
     * to make a move
     * @return 
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
    
    /**
     * Sets all the pieces in the starting positions
     * for use when a new board object is created
     */
    private void setStartPieces() {
        setWhitePieces();
        setBlackPieces();

    }

    /**
     * Places the white pieces in their starting spots
     */
    private void setWhitePieces() {
        int x;
        int y = 6;
        //Pawns
        for (x = 0; x < 8; x++) {
            BOARD[x][y] = new Pawn(x, y, "white", "pawn");
            whitePieces.add(BOARD[x][y]);
        }
        //Rooks
        BOARD[0][7] = new Rook(0, 7, "white", "rook");
        BOARD[7][7] = new Rook(7, 7, "white", "rook");

        //Knights
        BOARD[1][7] = new Knight(1, 7, "white", "knight");
        BOARD[6][7] = new Knight(6, 7, "white", "knight");

        //Bishops
        BOARD[2][7] = new Bishop(2, 7, "white", "bishop");
        BOARD[5][7] = new Bishop(5, 7, "white", "bishop");

        //King
        BOARD[4][7] = new King(4, 7, "white", "king");

        //Queen
        BOARD[3][7] = new Queen(3, 7, "white", "queen");

        //add the rest of the pieces to the list of active pieces
        for (int i = 0; i < 8; ++i) {
            whitePieces.add(BOARD[i][7]);
        }
    }

    /**
     * Places the black pieces in their starting spots
     */
    private void setBlackPieces() {
        int x;
        int y = 1;
        //Pawns
        for (x = 0; x < 8; x++) {
            BOARD[x][y] = new Pawn(x, y, "black", "pawn");
            blackPieces.add(BOARD[x][y]);
        }
        //Rooks
        BOARD[0][0] = new Rook(0, 0, "black", "rook");
        BOARD[7][0] = new Rook(7, 0, "black", "rook");

        //Knights
        BOARD[1][0] = new Knight(1, 0, "black", "knight");
        BOARD[6][0] = new Knight(6, 0, "black", "knight");

        //Bishops
        BOARD[2][0] = new Bishop(2, 0, "black", "bishop");
        BOARD[5][0] = new Bishop(5, 0, "black", "bishop");

        //King
        BOARD[4][0] = new King(4, 0, "black", "king");

        //Queen
        BOARD[3][0] = new Queen(3, 0, "black", "queen");

        //add the rest of the pieces to the list of active pieces
        for (int i = 0; i < 8; ++i) {
            blackPieces.add(BOARD[i][0]);
        }
    }
    
    
    private boolean isInBounds(int colNum, int rowNum) {
        return colNum >= 0 && colNum < 8 && rowNum >= 0 && rowNum < 8;
    }
    
    /**
     * Provides move validation for a requested move
     * as it relates to the board during game play
     * @param move
     * @return 
     */
    public boolean move(Move move) {
        //Check the bounds of the starting position
    if (!isInBounds(move.FROM_COL, move.FROM_ROW)) {
            return false;
        }

        //Check if the player whose turn it is has a piece at the specified starting position
        Piece piece = BOARD[move.FROM_COL][move.FROM_ROW];
        if (piece == null || (isWhiteTurn && piece.getColor().equals("black")) || (!isWhiteTurn && piece.getColor().equals("white"))) {
            return false;
        }
        //Check if the move is valid for the piece and ensure that the king is not in check after the move
        boolean isValid = piece.isValidMove(this, move.TO_COL, move.TO_ROW);
        //Make the requested move if it is valid
        if (isValid) {
            //Move the piece
            BOARD[move.FROM_COL][move.FROM_ROW] = null;
            BOARD[move.TO_COL][move.TO_ROW] = piece;
            piece.setColNum(move.TO_COL);
            piece.setRowNum(move.TO_ROW);
            
            Piece captured = BOARD[move.TO_COL][move.TO_ROW];

            //if a piece was captured, remove it from the list of active pieces
            if (captured != null) {
                if (captured.getColor().equals("white")) {
                    whitePieces.remove(captured);
                } else {
                    blackPieces.remove(captured);
                }               
            }
        }
        return isValid;
    }
    
    
    /**
     * Prints game board to the console for testing purposes
     */
    public void printBoard() {

        String type;

        for (int y = 0; y < BOARD.length; y++) {
            for (int x = 0; x < BOARD.length; x++) {
                if (BOARD[x][y] != null) {
                    type = BOARD[x][y].getType();
                    switch (type) {
                        case "pawn":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" p ");
                            } else {
                                System.out.print(" P ");
                            }
                            break;
                        case "bishop":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" b ");
                            } else {
                                System.out.print(" B ");
                            }
                            break;
                        case "king":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" k ");
                            } else {
                                System.out.print(" K ");
                            }
                            break;
                        case "knight":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" n ");
                            } else {
                                System.out.print(" N ");
                            }
                            break;
                        case "queen":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" q ");
                            } else {
                                System.out.print(" Q ");
                            }
                            break;
                        case "rook":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                System.out.print(" r ");
                            } else {
                                System.out.print(" R ");
                            }
                            break;

                    }
                } else {
                    System.out.print(" • ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Returns a String representation of the game board
     * 
     * @return 
     */
    public String boardString() {
        String board = "";
        String type;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (BOARD[x][y]!=null){
                    type = BOARD[x][y].getType();
                    switch (type) {
                        case "pawn":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" p ");
                            } else {
                                board +=(" P ");
                            }
                            break;
                        case "bishop":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" b ");
                            } else {
                                board +=(" B ");
                            }
                            break;
                        case "king":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" k ");
                            } else {
                                board +=(" K ");
                            }
                            break;
                        case "knight":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" n ");
                            } else {
                                board +=(" N ");
                            }
                            break;
                        case "queen":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" q ");
                            } else {
                                board +=(" Q ");
                            }
                            break;
                        case "rook":
                            if ("black".equals(BOARD[x][y].getColor())) {
                                board +=(" r ");
                            } else {
                                board +=(" R ");
                            }
                            break;

                    }
                } else {
                    board += " • ";
                }
            }
            board += "\n";
    	}
        return board;
    }
    
    public int length(){
        int length = BOARD.length;
        return length;
    }
} 