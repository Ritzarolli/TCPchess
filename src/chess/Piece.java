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
public abstract class Piece {
    
    protected int colNum; //column position
    protected int rowNum; //row position
    protected final String COLOR;
    protected final String TYPE;

    public Piece(int x, int y, String color, String type) {
        this.COLOR = color;
        this.TYPE = type;
        this.colNum = x;
        this.rowNum = y;
    }

    /**
     * Gets the color of the piece
     *
     * @return the color of the piece
     */
    public String getColor() {
        return this.COLOR;
    }

    /**
     * Gets column for current position of piece
     *
     * @return current column position
     */
    public int getColNum() {
        return this.colNum;
    }

    /**
     * Sets column for position of piece
     *
     * @param x the column given for new position of piece
     */
    public void setColNum(int x) {
        this.colNum = x;
    }

    /**
     * Gets row for current position of piece
     *
     * @return current row position
     */
    public int getRowNum() {
        return this.rowNum;
    }

    /**
     * Sets row for position of piece
     *
     * @param y the row given for new position of piece
     */
    public void setRowNum(int y) {
        this.rowNum = y;
    }

    /**
     * Gets the type of the piece
     *
     * @return piece type
     */
    public String getType() {
        return this.TYPE;
    }

    /**
     * Checks if the given move is valid
     *
     * @param board The board on which the move is being checked
     * @param targetCol the column number of the new position
     * @param targetRow the row number of the new position
     * @return true if the move is valid and false otherwise
     */
    public abstract boolean isValidMove(Board board, int targetCol, int targetRow);

    /**
     * Checks if the piece has any available valid moves
     *
     * @param board the board on which the piece is stored
     * @return true if the piece has a valid move available and false otherwise
     */
    public abstract boolean hasValidMoves(Board board);

    /**
     * Checks if the given position is in the bounds of the board.
     *
     * @param targetCol the column of the position that is being tested
     * @param targetRow the row of the position that is being tested
     * @return true if the given position is within the bounds of the board and
     * false otherwise
     */
    protected final boolean isInBounds(int targetCol, int targetRow) {
        return targetCol >= 0 && targetCol < 8 && targetRow >= 0 && targetRow < 8;
    }
}
