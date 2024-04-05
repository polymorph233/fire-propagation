package org.example.io;

import org.example.core.Game;
import org.example.core.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConfigReader {

    /**
     * File name to read from
     */
    private String filename;

    /**
     * Initialize a new configuration reader
     * @param filename the path of config file
     */
    public ConfigReader(String filename) {
        this.filename = filename;
    }

    /**
     * This method suppress the IOException check of BufferedReader.readLine which prevent a fluent style
     * of chaining Optional flatMap calls.
     * @param reader the BufferReader to read
     * @return a string of next line it reads, as the {@link BufferedReader#readLine()}
     */
    private String readLineUnchecked(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("IOException was thrown in readLineUnchecked and suppressed, reason: " + e.getMessage());
        }
    }

    public Game buildGame() {

        // Open the file and handle potential IO Exceptions - that's caused by this file opening
        // at the end of this try-block
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // Read four first lines of the config file, to build four parameters:
            // height, width, propagationRate, initCount

            // Java lacks good support for null coalescing so we have to chain flatMaps
            // but it prevents use from repeating 4 nested if-null checks (and handling IO exceptions)
            var configuration = Optional.ofNullable(readLineUnchecked(reader))
                .flatMap(heightLine -> Optional.ofNullable(readLineUnchecked(reader))
                    .flatMap(widthLine -> Optional.ofNullable(readLineUnchecked(reader))
                        .flatMap(propLine -> Optional.ofNullable(readLineUnchecked(reader))
                            .flatMap(countLine ->{
                                // A rough try-block to parse numbers from 4 read lines and build a configuration object
                                try {
                                    var height = Integer.parseInt(heightLine.substring(8));
                                    var width = Integer.parseInt(widthLine.substring(7));
                                    var propagationRate = Double.parseDouble(propLine.substring(13));
                                    var initCount = Integer.parseInt(countLine.substring(12));
                                    var ret = new Configuration();
                                    ret.height = height;
                                    ret.width = width;
                                    ret.propagationRate = propagationRate;
                                    ret.initCount = initCount;
                                    // If reached here, every thing is ok so a configuration object is built and
                                    // it would be returned as an optional that holds it
                                    return Optional.of(ret);
                                } catch (Exception e) {
                                    // Due to any reason, the parsing failed, so we return an optional which is empty
                                    // (equiv. to null)
                                    return Optional.empty();
                                }
                            }))));

            // If configuration was successfully built, we know how many init. coo should be read, so proceed to next stage
            configuration = configuration.flatMap(conf -> {
                // Loop through `initCount` count, we profit from the for loop of Java
                for (var i = 0; i < conf.initCount; i++) {
                    var line = readLineUnchecked(reader);
                    // If no enough read, config will be set to empty optional
                    if (line == null) {
                        return Optional.empty();
                    } else {
                        // Similar to previous try-block to catch any potential error
                        try {
                            var currPair = Arrays.stream(line.split(", ")).map(Integer::parseInt).collect(Collectors.toList());
                            conf.appendCoo(new Pair<>(currPair.get(0), currPair.get(1)));
                        } catch (Exception e) {
                            // Any error raised will result in an empty optional
                            return Optional.empty();
                        }
                    }
                }
                return Optional.of(conf);
            });

            // Check if optional holds the config object
            if (configuration.isPresent()) {
                // It presents, so safe to get
                var conf = configuration.get();
                // Check if it's safe to build a game
                if (conf.isValidConfiguration()) {
                    return new Game(conf.height, conf.width, conf.propagationRate, conf.coos);
                }
            }

            // Any other case that file is read but invalid
            throw new IllegalStateException("Invalid configuration during reader setup");

        } catch (IOException e) {
            System.out.println("The configuration file \"" + filename + "\" does not exist!");
            throw new IllegalStateException("The configuration file \"" + filename + "\" does not exist!");
        }
    }
}
