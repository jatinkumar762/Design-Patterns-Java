import dtos.Move;
import enums.Color;
import enums.GameState;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChessGame {

    private Board board;
    private List<Piece> whiteCapturedPieces;
    private List<Piece> blackCapturedPieces;
    private GameState gameState;
    private Color currentTurn;

    public ChessGame(){
        board = new Board();
        this.whiteCapturedPieces = new ArrayList<>();
        this.blackCapturedPieces = new ArrayList<>();
        this.currentTurn = Color.WHITE;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void startGame(){
        gameState = GameState.IN_PROGRESS;
    }

    public void stopGame(String message){
        System.out.println(message);
        gameState = GameState.FINISHED;
    }

    public boolean makeMove(Move move){

        if(!GameState.IN_PROGRESS.equals(gameState)){
            System.out.println("Game is not active.");
            return false;
        }

        Piece movingPiece = board.getPiece(move.getFromRow(), move.getFromCol());

        if(Objects.isNull(movingPiece)){
            System.out.println("No Piece exist");
            return false;
        }

        board.movePiece(move, currentTurn);

        if (board.isCheckMate(oppositeColor())) {
            stopGame(currentTurn + " wins by checkmate!");
            return true;
        } else if (board.isStalemate(oppositeColor())) {
            stopGame("Stalemate.");
            return true;
        }

        this.currentTurn = oppositeColor();

        return false;
    }


    public Color oppositeColor(){
        return this.currentTurn.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
}
