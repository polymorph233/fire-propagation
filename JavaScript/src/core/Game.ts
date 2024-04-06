import {Board} from "./Board";

/**
 * The game engine, which initializes a condition and dispatch methods to the board
 */
class Game {
  private board: Board
  private propagationCondition: () => boolean

  /**
   * Initialize a new game
   * @param height height of the board, must be an absolute positive int
   * @param width width of the board, must be an absolute positive int
   * @param propagationRate decides how often a fire will propagate to its neighbors, between 0.0 and 1.0, where
   *                        0.0 means never and 1.0 means always
   * @param initCoos initial coos, in shape of Array<Array<int>> where the inner array has only two elements to
   *                 indicate y and x
   */
  constructor(height: number, width: number, propagationRate: number, initCoos: number[][]) {
    this.board = new Board(height, width, initCoos)
    this.propagationCondition = () => {
      let curr = Math.random();
      return curr < propagationRate;
    }
  }

  /**
   * Proceed the game to next turn, it returns nothing
   */
  nextTurn() {
    this.board.updateBoard(this.propagationCondition)
  }

  /**
   * Proceed the game to next turn and *returns* the current board state in multi-lines style
   */
  printAndAdvance() {
    const currState = this.board.toString()
    this.nextTurn()
    return currState
  }

  /**
   * Peek the current board state in multi-lines style
   */
  peek() {
    return this.board.toString()
  }

  /**
   * Peek the current board state in single-line style
   */
  produceStateSequence()  {
    return this.board.toStringTrimmed()
  }

  /**
   * Check if the game is finished
   */
  isGameFinished() {
    return this.board.isGameFinished()
  }

}

export { Game }