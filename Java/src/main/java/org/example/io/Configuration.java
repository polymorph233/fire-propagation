package org.example.io;

import org.example.core.Pair;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    Integer height = null;
    Integer width = null;
    Double propagationRate = null;
    Integer initCount = null;
    List<Pair<Integer, Integer>> coos = new ArrayList<>();

    boolean isValidConfiguration() {
        return height != null
                && width != null
                && propagationRate != null
                && initCount != null
                && coos.size() == initCount;
    }

    void appendCoo(Pair<Integer, Integer> coo) {
        this.coos.add(coo);
    }
}
