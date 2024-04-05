package org.example.core;

/**
 * A generic pair data type, here is used for holding coordinates
 * @param <T> first element
 * @param <U> second element
 */
public class Pair<T, U> {

    /**
     * First element
     */
    public T first;

    /**
     * Second element
     */
    public U second;

    /**
     * Create a new pair
     * @param first first element
     * @param second second element
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
