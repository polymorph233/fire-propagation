package org.example;


import org.example.io.ConfigReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var configReader = new ConfigReader();

        try {
            var game = configReader.buildGame();

            while (!game.isGameFinished()) {
                var currSnap = game.printAndAdvance();
                for (var line : currSnap) {
                    for (var sqState : line) {
                        System.out.print(sqState);
                    }
                    System.out.println();
                }
                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}