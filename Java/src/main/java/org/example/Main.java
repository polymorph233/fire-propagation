package org.example;


import org.example.io.ConfigReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide path to your configuration file");
            System.exit(0);
        }
        var configReader = new ConfigReader(args[0]);


        var game = configReader.buildGame();

        while (!game.isGameFinished()) {
            System.out.println(game.printAndAdvance());
        }

    }
}