package org.example.core;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Game {

    private Board board;

    private Random random;

    private Supplier<Boolean> propagationCondition;

    public Game(int height, int width, double propagationRate, List<Pair<Integer, Integer>> initCoos) {
        this.board = new Board(height, width, initCoos);
        this.random = new Random();
        this.propagationCondition = () -> {
            var curr = random.nextDouble();
            if (curr < propagationRate) {
                return true;
            } else {
                return false;
            }
        };
    }

    public void nextTurn() {
        this.board.updateBoard(propagationCondition);
    }

    public String printAndAdvance() {
        var currState = this.board.toString();
        nextTurn();
        return currState;
    }

    public String peek() {
        return this.board.toString();
    }
}
