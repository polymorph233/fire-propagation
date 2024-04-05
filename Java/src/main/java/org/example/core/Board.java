package org.example.core;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A board represent a playground where the game is launched and played through
 * steps.
 */
public class Board {

    /**
     * Game board is represented by a 2d array of squares
     */
    private Square[][] board;

    /**
     * If the game is finished, this flag is set to true to prevent unnecessary calculations
     */
    private boolean gameFinished;


    /**
     * Initialize a new board
     * @param height height of the board
     * @param width width of the board
     * @param initCoos initial coordinates that are set on fire in first turn
     */
    public Board(int height, int width, List<Pair<Integer, Integer>> initCoos) {
        this.board = new Square[height][width];
        this.gameFinished = false;
        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                this.board[i][j] = new Square(State.TREE);
            }
        }
        for (var coo : initCoos) {
            var i = coo.first;
            var j = coo.second;
            // Only take into account coordinates that are in range of the board
            if (0 <= i && i < height && 0 <= j && j < width) {
                this.board[coo.first][coo.second] = new Square(State.ON_FIRE);
            }
        }
    }

    /**
     * Check if a given location (i, j) has at least one neighbor that's on fire
     * @param i the offset on height axis
     * @param j the offset on width axis
     * @return true if it's the case or false
     */
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

    /**
     * Check if the game is finished. <br>
     * In a board, there is a variable `gameFinished` which will be set to true once the game is finished.
     * This method looks up this variable and return true if it's true, otherwise it will iterate through
     * the board to check if any square is on fire, which indicates that it's not finished, and update the
     * variable accordingly. <br>
     * Warning: this operation could be time-consuming if board is large.
     * @return true if the game is finished
     */
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

    /**
     * Perform the game into next iteration <br>
     * It takes a lambda that defines the propagation rate of this game, to be effective in this next iteration.
     * @param thresholdCondition a lambda of type () -> Boolean, where you can encapsulate
     *                           a random generator to provide randomness, or simply return
     *                           true if you want to set everywhere on fire, or false if you
     *                           want to stop the fire immediately.
     */
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
