package app;

import app.enums.Symbol;

public class Dashboard {
    private int size;
    private Symbol[][] board;

    public Dashboard(int size) {
        this.size = size;
        initializeBoard();
    }

    private void initializeBoard() {
        board = new Symbol[size][size];
    }

    public void setPosition(int row, int col, Symbol symbol){

        if(row < 0 || row > size || col < 0 || col > size){
            Logger.log("invalid move");
            return;
        }

        if(board[row][col]!=null){
            Logger.log("position already occupied by player "+ board[row][col].name());
            return;
        }

        board[row][col] = symbol;
    }

    public boolean isWinner(Symbol symbol){

        // Check rows
        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            for (int j = 0; j < size; j++) {
                if (board[i][j]==null || !board[i][j].equals(symbol)) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            boolean colWin = true;
            for (int i = 0; i < size; i++) {
                if (board[i][j]==null || !board[i][j].equals(symbol)) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }

        // Diagonal
        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < size; i++) {
            if (board[i][i]==null || !board[i][i].equals(symbol)) diag1 = false;
            if (board[i][size - i - 1]==null || !board[i][size - i - 1].equals(symbol)) diag2 = false;
        }

        return diag1 || diag2;
    }


    public void show(){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
