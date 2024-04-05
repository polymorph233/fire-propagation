package org.example.io;

import org.example.core.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A data type to hold configuration and check if it's valid
 */
public class Configuration {

    // All fields are initially empty (null or empty list)

    Integer height = null;
    Integer width = null;
    Double propagationRate = null;
    Integer initCount = null;
    List<Pair<Integer, Integer>> coos = new ArrayList<>();

    /**
     * Check if current configuration object is valid
     * @return true if it's valid or false
     */
    boolean isValidConfiguration() {
        return height != null
                && width != null
                && propagationRate != null
                && initCount != null
                && coos.size() == initCount;

        // Maybe we could check if height and width are strictly positive?
    }

    /**
     * Append a coordinate to this configuration
     * @param coo a pair of ints (coordinate) to append
     */
    void appendCoo(Pair<Integer, Integer> coo) {
        this.coos.add(coo);
    }
}
