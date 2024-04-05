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

    private String filename;

    public ConfigReader(String filename) {
        this.filename = filename;
    }

    private String readLineUnchecked(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("IOException was thrown in readLineUnchecked and suppressed, reason: " + e.getMessage());
        }
    }

    public Game buildGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {


            var configuration = Optional.ofNullable(readLineUnchecked(reader))
                .flatMap(lengthLine -> Optional.ofNullable(readLineUnchecked(reader))
                    .flatMap(widthLine -> Optional.ofNullable(readLineUnchecked(reader))
                        .flatMap(propLine -> Optional.ofNullable(readLineUnchecked(reader))
                            .flatMap(countLine ->{
                                var length = Integer.parseInt(lengthLine.substring(8));
                                var width = Integer.parseInt(widthLine.substring(7));
                                var propagationRate = Double.parseDouble(propLine.substring(13));
                                var initCount = Integer.parseInt(countLine.substring(12));
                                var ret = new Configuration();
                                ret.height = length;
                                ret.width = width;
                                ret.propagationRate = propagationRate;
                                ret.initCount = initCount;
                                return Optional.of(ret);
                            }))));

            configuration.flatMap(conf -> {
                for (var i = 0; i < conf.initCount; i++) {
                    var line = readLineUnchecked(reader);
                    if (line == null) {
                        return Optional.empty();
                    } else {
                        var currPair = Arrays.stream(line.split(", ")).map(Integer::parseInt).collect(Collectors.toList());
                        conf.appendCoo(new Pair<>(currPair.get(0), currPair.get(1)));
                    }
                }
                return Optional.of(conf);
            });

            if (configuration.isPresent()) {
                var conf = configuration.get();
                if (conf.isValidConfiguration()) {
                    return new Game(conf.height, conf.width, conf.propagationRate, conf.coos);
                }
            }

            throw new IllegalStateException("Invalid configuration during reader setup");

        } catch (IOException e) {
            System.out.println("The configuration file \"" + filename + "\" does not exist!");
            throw new IllegalStateException("The configuration file \"" + filename + "\" does not exist!");
        }
    }
}
