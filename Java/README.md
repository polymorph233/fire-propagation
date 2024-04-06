# Fire propagation game

A simple game to simulate fire propagation

## Usage

```bash

# check Java version
$ java --version

# ensure you have a JDK of at least version 11

# Navigate to root path
$ cd src/main/java

# Build project
$ javac org/example/Main.java

# Check help (You can get an example of a valid config file)
$ java org.example.Main help

# Run the simulation (suppose that your config file is stored in path `config`
$ java org.example.Main load config

```

## Parameters

- length: the height of the board
- width: the width of the board
- propagation: a rate to decide if a square adjacent to fire will be on fire, between 0.0 and 1.0
- init count: how many initial spots are on fire

This should be followed by `init count` lines of pairs of ints, in form like `1, 2`:

```
1, 2
2, 3
```