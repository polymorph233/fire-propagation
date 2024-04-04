import {Square, State} from "./Square";

class Board {
  private board: Square[][]
  private gameFinished: boolean

  constructor(height: number, width: number, initCoos: number[][]) {
    this.board = []
    for (let i = 0 ; i < height; i++) {
      let line: Square[] = []
      for (let j = 0; j < width; j++) {
        line.push(new Square(State.TREE))
      }
      this.board.push(line)
    }
    this.gameFinished = false

    for (let coo of initCoos) {
      this.board[coo[0]][coo[1]] = new Square(State.ON_FIRE)
    }
  }

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

  updateBoard(thresholdCondition) {
    if (this.gameFinished) {
      console.log("Game is already finished, nothing left");
      return;
    }

    var newBoard: Square[][] = []
    for (let i = 0 ; i < this.board.length; i++) {
      let line: Square[] = []
      for (let j = 0; j < this.board[0].length; j++) {
        line.push(new Square(State.TREE))
      }
      newBoard.push(line)
    }
    var dirty = false;

    for (var i = 0; i < this.board.length; i++) {
      for (var j = 0; j < this.board.length; j++) {
        switch (this.board[i][j].getState()) {
          case State.TREE: {
            if (this.neighborOnFireAtLastIteration(i, j)) {
              // If the condition holds ... the fire propage to this square
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
    this.board = newBoard;
    if (!dirty) {
      this.gameFinished = true;
    }
  }

  toString() {
    return this.board.map(line => line.join('')).join('\n')
  }
}

export { Board }