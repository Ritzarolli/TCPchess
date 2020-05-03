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
public class Move {
    /**
     * Starting column position
     */
    public final int FROM_COL;

    /**
     * Starting row position
     */
    public final int FROM_ROW;

    /**
     * Column position to move to
     */
    public final int TO_COL;

    /**
     * Row position to move to
     */
    public final int TO_ROW;

    /**
     * Creates a move with the given starting position and target position
     *
     * @param startX the column of the starting position
     * @param startY the row of the starting position
     * @param endX the column of the target position
     * @param endY the row of the target position
     */
    public Move(int startX, int startY, int endX, int endY) {
        this.FROM_COL = startX;
        this.FROM_ROW = startY;
        this.TO_COL = endX;
        this.TO_ROW = endY;
    }
}