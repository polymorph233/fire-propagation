package org.example.core;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * This class simulates a game engine that take into account a board and executes the game.
 * Most of the core logics are inside the {@link Board} type, but this class serves mainly to
 * configure the `propagationCondition`. <br>
 * It could be a starting point for building more complex configurations.
 */
public class Game {

    /**
     * An instance of {@link Board} that it proxies to
     */
    private Board board;

    /**
     * Randomness source provider
     */
    private Random random;

    /**
     * A lambda that encapsulates the propagation condition
     */
    private Supplier<Boolean> propagationCondition;

    /**
     * Initialize the game
     * @param height height of the board
     * @param width width of the board
     * @param propagationRate a floating value to indicate the rate of setting an adjacent square on
     *                        fire, it should be set between (inclusive) 0.0 and 1.0.
     *                        0.0 means no adjacent square will on fire and 1.0 means that any adjacent
     *                        square will be set on fire.
     * @param initCoos initial coordinates in form of a list. Any coordinate that's out of range will
     *                 be discarded.
     */
    public Game(int height, int width, double propagationRate, List<Pair<Integer, Integer>> initCoos) {
        this.board = new Board(height, width, initCoos);
        this.random = new Random();

        // Set up the propagation condition, it would simply return true or false according to the given rate
        this.propagationCondition = () -> {
            var curr = random.nextDouble();
            if (curr < propagationRate) {
                return true;
            } else {
                return false;
            }
        };
    }

    /**
     * Move the game into next iteration, it calls the board to update simply
     */
    public void nextTurn() {
        this.board.updateBoard(propagationCondition);
    }

    /**
     * Move the game into next iteration *while* returning the *current* board status
     * @return a string representing the board's current status
     */
    public String printAndAdvance() {
        var currState = this.board.toString();
        nextTurn();
        return currState;
    }

    /**
     * Inspect the current state of the board without advancing it
     * @return a string representing the board's current status
     */
    public String peek() {
        return this.board.toString();
    }

    /**
     * Check if the game is finished
     * @return true if game is finished, otherwise false
     */
    public boolean isGameFinished() {
        return board.isGameFinished();
    }
}
