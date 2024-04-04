package org.example.core;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Board {

    private Square[][] board;

    private boolean gameFinished;

    public Board(int height, int width, List<Pair<Integer, Integer>> initCoos) {
        this.board = new Square[height][width];
        this.gameFinished = false;
        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                this.board[i][j] = new Square(State.TREE);
            }
        }
        for (var coo : initCoos) {
            this.board[coo.first][coo.second] = new Square(State.ON_FIRE);
        }
    }

    private boolean neighborOnFireAtLastIteration(int i, int j) {
        if (i > 0 && board[i-1][j].getState() == State.ON_FIRE) {
            return true;
        }
        if (i < board.length - 1 && board[i+1][j].getState() == State.ON_FIRE) {
            return true;
        }
        if (j > 0 && board[i][j-1].getState() == State.ON_FIRE) {
            return true;
        }
        if (j < board[0].length - 1 && board[i][j+1].getState() == State.ON_FIRE) {
            return true;
        }
        return false;
    }

    public boolean isGameFinished() {
        if (gameFinished) {
            return true;
        } else {
            for (var line : board) {
                for (var sq : line) {
                    if (sq.getState() == State.ON_FIRE) {
                        return false;
                    }
                }
            }
            gameFinished = true;
            return true;
        }
    }

    public void updateBoard(Supplier<Boolean> thresholdCondition) {
        if (gameFinished) {
            System.out.println("Game is already finished, nothing left");
            return;
        }

        var newBoard = new Square[board.length][board[0].length];
        var dirty = false;

        for (var i = 0; i < board.length; i++) {
            for (var j = 0; j < board.length; j++) {
                switch (board[i][j].getState()) {
                    case TREE:
                        if (neighborOnFireAtLastIteration(i, j)) {
                            // If the condition holds ... the fire propage to this square
                            if (thresholdCondition.get()) {
                                newBoard[i][j] = new Square(State.ON_FIRE);
                                dirty = true;
                            // Neighbor was on fire but this square is lucky
                            } else {
                                newBoard[i][j] = board[i][j];
                            }
                        // No neighbor on fire, it's safe
                        } else {
                            newBoard[i][j] = board[i][j];
                        }
                        break;
                    case ON_FIRE:
                        newBoard[i][j] = new Square(State.ASHES);
                        dirty = true;
                        break;
                    case ASHES:
                        newBoard[i][j] = board[i][j];
                        break;
                }
            }
        }
        board = newBoard;
        if (!dirty) {
            gameFinished = true;
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var line : board) {
            for (var sq : line) {
                builder.append(sq.toString());
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
