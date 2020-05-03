/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author mnhammond0
 */
public class Rook extends Piece {
    
    public Rook(int x, int y, String color, String type) {
        super(x, y, color, type);
    }   

    @Override
    public boolean isValidMove(Board board, int targetCol, int targetRow) {
        return isInBounds(targetCol, targetRow); 
        //TODO add more complex chess rules
    }

    @Override
    // moves side to side
    public boolean hasValidMoves(Board board) {
        return isValidMove(board, colNum - 1, rowNum)
                || isValidMove(board, colNum + 1, rowNum)
                || isValidMove(board, colNum, rowNum - 1)
                || isValidMove(board, colNum, rowNum + 1);
    }
}
