import {Board} from "./Board";

class Game {
  private board: Board
  private propagationCondition: () => boolean

  constructor(height: number, width: number, propagationRate: number, initCoos: number[][]) {
    this.board = new Board(height, width, initCoos)
    this.propagationCondition = () => {
      let curr = Math.random();
      return curr < propagationRate;
    }
  }

  nextTurn() {
    this.board.updateBoard(this.propagationCondition)
  }

  printAndAdvance() {
    const currState = this.board.toString()
    this.nextTurn()
    return currState
  }

  peek() {
    return this.board.toString()
  }

  isGameFinished() {
    return this.board.isGameFinished()
  }
}

export { Game }