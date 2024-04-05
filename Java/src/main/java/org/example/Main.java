package org.example;


import org.example.io.ConfigReader;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        System.out.println("Fire propagation game");

        if (args.length == 1) {
            if ("help".equals(args[0])) {
                System.out.println("usage: help | load <filepath>");
                System.out.println("Example of valid config:");
                System.out.println();
                System.out.println("length: 5");
                System.out.println("width: 5");
                System.out.println("propagation: 0.5");
                System.out.println("init count: 2");
                System.out.println("1, 2");
                System.out.println("2, 3");
                System.out.println();
                System.out.println();
                System.exit(0);
            }
        }
        if (args.length == 2) {
            if ("load".equals(args[0])) {
                var configReader = new ConfigReader(args[1]);


                var game = configReader.buildGame();

                while (!game.isGameFinished()) {
                    System.out.println(game.printAndAdvance());
                }
                System.exit(0);
            }
        }
        System.out.println("usage: help | load <filepath>");
        System.exit(0);
    }
}