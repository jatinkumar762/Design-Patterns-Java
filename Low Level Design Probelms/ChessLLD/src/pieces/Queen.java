package pieces;

import enums.Color;

public class Queen extends Piece {

    public Queen(Color color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean canMove(int destRow, int destCol, Piece[][] board) {
        return false;
    }
}
