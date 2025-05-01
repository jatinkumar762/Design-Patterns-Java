package dtos;

public class Move {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    public Move(int fromCol, int fromRow, int toCol, int toRow) {
        this.fromCol = fromCol;
        this.fromRow = fromRow;
        this.toCol = toCol;
        this.toRow = toRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getToCol() {
        return toCol;
    }

    public int getToRow() {
        return toRow;
    }
}
