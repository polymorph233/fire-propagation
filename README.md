# Fire Propagation

A simple fire propagation game, implemented respectively in Java and JavaScript (TypeScript/Vue.js).

## Java client

Java version 11 or newer is required.

Usage:

```bash

# change directory to the root of Java program packages
$ cd Java/src/main/java

# compile the program with Main entrypoint
$ javac org/example/Main.java

# a sample config file is provided in the repo, you can use it or write your own
# following its syntax
$ cp ../../../config .

# run the game
$ java org.example.Main config

```

## JavaScript application

A simple vue.js SPA application to visualize the same game in your browser.

Usage:

```bash
# install dependencies
$ npm install

# setup vue.js server
$ npm run dev
```

Then you can navigate to your application (normally http://localhost:5173) and play the game.

You will need to input your parameters in your first page then go to the game page to see the
game plays.

Notice that currently you have to write freeform input for init. fire spots in the shape of
`1-1, 2-2, 3-3` i.e. using commas to separate coordinates and dashes to connect two bodies of
coordinates. This feature is planned to be removed by a better input method.

