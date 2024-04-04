package org.example;


import org.example.io.ConfigReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var configReader = new ConfigReader();

        try {
            var game = configReader.buildGame();

            while (!game.isGameFinished()) {
                System.out.println(game.printAndAdvance());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}