/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 * Move logic for the Queen pieces
 * @author mnhammond0
 */
public class Queen extends Piece {

    public Queen(int x, int y, String color, String type) {
        super(x, y, color, type);
    }   

    @Override
    public boolean isValidMove(Board board, int targetCol, int targetRow) {
        return isInBounds(targetCol, targetRow); 
        //TODO add more complex chess rules
    }

    @Override
    public boolean hasValidMoves(Board board) {
        for (int i = colNum - 1; i <= rowNum + 1; ++i) {
            for (int j = rowNum - 1; j <= rowNum + 1; ++j) {
                if (isValidMove(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
