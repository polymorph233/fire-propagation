package org.example.core;

public class Square {

    private State state;

    public Square(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    @Override
    public String toString() {
        switch (state) {
            case TREE:
                return "O";
            case ON_FIRE:
                return "X";
            case ASHES:
                return "_";
            default:
                throw new IllegalStateException("Impossible arm");
        }
    }
}
