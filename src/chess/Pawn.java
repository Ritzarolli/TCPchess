/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 * Move logic for the Pawns
 * @author mnhammond0
 */
public class Pawn extends Piece {
    
    public Pawn(int x, int y, String color, String type) {
        super(x, y, color, type);
    }   

    @Override
    public boolean isValidMove(Board board, int targetCol, int targetRow) {
        return isInBounds(targetCol, targetRow); 
        //TODO add more complex chess rules
    }

    @Override
    public boolean hasValidMoves(Board board) {
        if (COLOR.equals("black")) {
            return isValidMove(board, colNum, rowNum + 1)
                    || isValidMove(board, colNum, rowNum + 2)
                    || isValidMove(board, colNum - 1, rowNum + 1)
                    || isValidMove(board, colNum + 1, rowNum + 1);
        } else {
            return isValidMove(board, colNum, rowNum - 1)
                    || isValidMove(board, colNum, rowNum - 2)
                    || isValidMove(board, colNum - 1, rowNum - 1)
                    || isValidMove(board, colNum + 1, rowNum - 1);
        }
    }
}