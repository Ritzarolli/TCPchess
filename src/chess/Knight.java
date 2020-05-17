/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 * Move logic for the Knight
 * @author mnhammond0
 */
public class Knight extends Piece {
    public Knight(int x, int y, String color, String type) {
        super(x, y, color, type);
    }   

    @Override
    public boolean isValidMove(Board board, int targetCol, int targetRow) {
        return isInBounds(targetCol, targetRow) && isLshaped(targetCol, targetRow); 
        //TODO add more complex chess rules
    }
    // checks that move is L-shaped
    private boolean isLshaped(int targetCol, int targetRow){
        int colDiff = (targetCol-colNum);
        int rowDiff = (targetRow-rowNum);
        return (colDiff==1 && rowDiff==2) || (colDiff==2 && rowDiff==1);
    }

    @Override
    // moves in 3x2 L shape
    public boolean hasValidMoves(Board board) {
        return isValidMove(board, colNum - 2, rowNum - 3)
                || isValidMove(board, colNum - 2, rowNum + 3)
                || isValidMove(board, colNum + 2, rowNum - 3)
                || isValidMove(board, colNum + 2, rowNum + 3)
                || isValidMove(board, colNum - 3, rowNum - 2)
                || isValidMove(board, colNum - 3, rowNum + 2)
                || isValidMove(board, colNum + 3, rowNum - 2)
                || isValidMove(board, colNum + 3, rowNum + 2);
    }
}
