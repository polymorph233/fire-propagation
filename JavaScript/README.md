# Fire propagation game

A simple game to simulate fire propagation implemented in TypeScript and vue.js

## Usage

```bash
# install dependencies
npm install

# run the game server
npm run dev

# open a browser to play
```

## Parameters

- length: the height of the board
- width: the width of the board
- propagation: a rate to decide if a square adjacent to fire will be on fire, between 0.0 and 1.0
- A list of coordinates to set on fire in first turn, which you will use the dedicated buttons to 
add and clear

You don't need to parse anything, provide a configuration file or take care of your input format,
they are handled by the game.