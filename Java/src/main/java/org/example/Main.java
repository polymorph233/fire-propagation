package org.example;

import org.example.core.Game;
import org.example.core.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("config"))) {
            String line = reader.readLine();

            var length = Integer.parseInt(line.substring(8));

            line = reader.readLine();

            var width = Integer.parseInt(line.substring(7));

            line = reader.readLine();

            var propagationRate = Double.parseDouble(line.substring(13));

            line = reader.readLine();

            var initCount = Integer.parseInt(line.substring(12));

            List<Pair<Integer, Integer>> initCoos = new ArrayList<>();

            for (var i = 0; i < initCount; i++) {
                line = reader.readLine();
                var currPair = Arrays.stream(line.split(", ")).map(Integer::parseInt).collect(Collectors.toList());
                initCoos.add(new Pair<Integer, Integer>(currPair.get(0), currPair.get(1)));
            }

            var game = new Game(length, width, propagationRate, initCoos);

            while (!game.isGameFinished()) {
                System.out.println(game.printAndAdvance());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}