/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 * Default move logic without regard to individual piece rules
 * @author mnhammond0
 */
public class King extends Piece {
    
    public King(int x, int y, String color, String type) {
        super(x, y, color, type);
    }   

    @Override
    public boolean isValidMove(Board board, int targetCol, int targetRow) {
        return isInBounds(targetCol, targetRow); 
        //TODO add more complex chess rules
    }

    @Override
    public boolean hasValidMoves(Board board) {
        for (int i = colNum - 1; i <= colNum + 1; ++i) {
            for (int j = rowNum - 1; j <= rowNum + 1; ++j) {
                if (isValidMove(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
