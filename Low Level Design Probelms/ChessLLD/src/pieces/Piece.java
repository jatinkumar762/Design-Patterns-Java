package pieces;

import enums.Color;

public abstract class Piece {
    private Color color;
    private int row, col;

    Piece(Color color, int row, int col){
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract boolean canMove(int destRow, int destCol, Piece[][] board);

    public void move(int destRow, int destCol){
        this.row = destRow;
        this.col = destCol;
    }
}
