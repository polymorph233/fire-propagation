package org.example.core;

/**
 * A simple data type to represent a square in the board <br>
 * Notice that this type is intended to be used as a datatype without mutations,
 * which means that you will create new copy of `Square` to replace an old one
 * instead of mutating it.
 */
public class Square {

    /**
     * State of this square
     */
    private State state;

    /**
     * Initialize a new square
     * @param state the state to be set
     */
    public Square(State state) {
        this.state = state;
    }

    /**
     * Getter of the state
     * @return the current {@link State} of this square
     */
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
