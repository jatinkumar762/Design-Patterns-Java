import dtos.Move;
import enums.Color;
import pieces.*;

import java.util.Objects;

public class Board {

    private Piece[][] board;
    private static final int ROWS = 8;
    private static final int COLS = 8;

    public Board(){
        board = new Piece[ROWS][COLS];
        initialize();
    }

    public void initialize(){

        board[0][0] = new Rook(Color.BLACK, 0, 0);
        board[0][1] = new Knight(Color.BLACK, 0, 1);
        board[0][2] = new Bishop(Color.BLACK, 0, 2);
        board[0][3] = new Queen(Color.BLACK, 0, 3);
        board[0][4] = new King(Color.BLACK, 0, 4);
        board[0][5] = new Bishop(Color.BLACK, 0, 5);
        board[0][6] = new Knight(Color.BLACK,0, 6);
        board[0][7] = new Rook(Color.BLACK, 0,7);

        //BLACK PAWN
        for(int col = 0; col < COLS; col++){
            board[1][col] = new Pawn(Color.BLACK, 1, col);
        }
        board[7][0] = new Rook(Color.WHITE, 7, 0);
        board[7][1] = new Knight(Color.WHITE, 7, 1);
        board[7][2] = new Bishop(Color.WHITE, 7, 2);
        board[7][3] = new Queen(Color.WHITE, 7, 3);
        board[7][4] = new King(Color.WHITE, 7, 4);
        board[7][5] = new Bishop(Color.WHITE, 7, 5);
        board[7][6] = new Knight(Color.WHITE, 7, 6);
        board[7][7] = new Rook(Color.WHITE, 7,7);

        //WHITE PAWN
        for(int col = 0; col < COLS; col++){
            board[6][col] = new Pawn(Color.WHITE, 1, col);
        }

    }

    public Piece getPiece(int row, int col){
        return board[row][col];
    }

    public boolean movePiece(Move move, Color currentPlayer){

        Piece piece = board[move.getFromRow()][move.getFromCol()];

        if(Objects.isNull(piece) || !piece.getColor().equals(currentPlayer)){
            return false;
        }

        if(!piece.canMove(move.getToRow(), move.getToCol(), board)){
            return false;
        }

        board[move.getFromRow()][move.getFromCol()] = null;

        piece.move(move.getToRow(), move.getToCol());

        return true;
    }

    public boolean isCheckMate(Color playerColor){

        return false;
    }

    public boolean isStalemate(Color playerColor){

        return false;
    }
}
