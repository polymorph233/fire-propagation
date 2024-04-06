import {Square, State} from "./Square";

/**
 * A board that represents the game and mutates it to proceed the game
 */
class Board {

  private board: Square[][]
  private gameFinished: boolean

  /**
   * Initialize a game board
   * @param height height of the board, must be an absolute positive int
   * @param width width of the board, must be an absolute positive int
   * @param initCoos initial coos, in shape of `Array<Array<int>>` where the inner array has only two elements
   *                 to indicate y and x
   */
  constructor(height: number, width: number, initCoos: number[][]) {
    this.board = []

    // Set every square to tree by default
    for (let i = 0 ; i < height; i++) {
      let line: Square[] = []
      for (let j = 0; j < width; j++) {
        line.push(new Square(State.TREE))
      }
      this.board.push(line)
    }
    // Set end game flag to false
    this.gameFinished = false

    // Fill in squares that's initially on fire by provided coo
    for (let coo of initCoos) {
      this.board[coo[0]][coo[1]] = new Square(State.ON_FIRE)
    }
  }

  /**
   * Check if the square of y=i, x=i has any adjacent square on fire
   * @param i first coordinate
   * @param j second coordinate
   */
  private neighborOnFireAtLastIteration(i: number, j: number) {
    if (i > 0 && this.board[i - 1][j].getState() == State.ON_FIRE) {
      return true;
    }
    if (i < this.board.length - 1 && this.board[i + 1][j].getState() == State.ON_FIRE) {
      return true;
    }
    if (j > 0 && this.board[i][j - 1].getState() == State.ON_FIRE) {
      return true;
    }
    if (j < this.board[0].length - 1 && this.board[i][j + 1].getState() == State.ON_FIRE) {
      return true;
    }
    return false;
  }

  /**
   * Check if the game is finished.
   *
   * This method uses the `#gameFinished` field to memorize the state in case game is finished,
   * otherwise it will iterate the whole grid each time the method is called.
   */
  isGameFinished() {
    if (this.gameFinished) {
      return true;
    } else {
      for (let line of this.board) {
        for (let sq of line) {
          if (sq.getState() == State.ON_FIRE) {
            return false;
          }
        }
      }
      this.gameFinished = true;
      return true;
    }
  }

  /**
   * Update the board to next round
   *
   * If the game is finished, it will do nothing.
   * @param thresholdCondition a lambda that decides if a square should on fire, in type of `() -> bool`
   */
  updateBoard(thresholdCondition: () => boolean) {
    if (this.gameFinished) {
      console.log("Game is already finished, nothing left");
      return;
    }

    // Initial a new board with new values, so that the last state of old board will not
    // interfere
    let newBoard: Square[][] = []
    for (let i = 0 ; i < this.board.length; i++) {
      let line: Square[] = []
      for (let j = 0; j < this.board[0].length; j++) {
        line.push(new Square(State.TREE))
      }
      newBoard.push(line)
    }

    // Local variable to store if the board has been modified
    let dirty = false;

    for (let i = 0; i < this.board.length; i++) {
      for (let j = 0; j < this.board.length; j++) {
        switch (this.board[i][j].getState()) {
          case State.TREE: {
            if (this.neighborOnFireAtLastIteration(i, j)) {
              // If the condition holds ... the fire propagate to this square
              if (thresholdCondition()) {
                newBoard[i][j] = new Square(State.ON_FIRE);
                dirty = true;
                // Neighbor was on fire but this square is lucky
              } else {
                newBoard[i][j] = this.board[i][j];
              }
              // No neighbor on fire, it's safe
            } else {
              newBoard[i][j] = this.board[i][j];
            }
            break;
          }
          case State.ON_FIRE: {
            newBoard[i][j] = new Square(State.ASHES);
            dirty = true;
            break;
          }
          case State.ASHES: {
            newBoard[i][j] = this.board[i][j];
            break;
          }
        }
      }
    }
    // Move the new board to the `this.board` variable
    this.board = newBoard;
    if (!dirty) {
      // update the flag if game is finished (i.e. no update was performed
      this.gameFinished = true;
    }
  }

  /**
   * Render a string where board is shown in lines concatenated by a new line
   *
   * It uses the `toString` method of Square
   */
  toString() {
    return this.board.map(line => line.join('')).join('\n')
  }

  /**
   * Render a string where board is shown in a single line that contains all squares in a run
   *
   * It uses the `toString` method of Square
   */
  toStringTrimmed() {
    return this.board.map(line => line.join('')).join('')
  }
}

export { Board }