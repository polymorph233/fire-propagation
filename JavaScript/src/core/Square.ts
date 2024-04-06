/**
 * Different states of a square
 */
enum State {
  TREE, ON_FIRE, ASHES
}

/**
 * A square, it holds a state and it does not mutate per se
 */
class Square{

  private readonly state: State

  constructor(state: State) {
    this.state = state
  }

  /**
   * Get the current state of this square
   */
  getState() {
    return this.state;
  }

  /**
   * Render the square to mnemos that could be easily switched against
   * @return one of three strings "_", "X" or "O" indicating ashes, fire or tree
   */
  toString() {
    switch (this.state) {
      case State.ASHES: return '_'
      case State.ON_FIRE: return 'X'
      case State.TREE: return 'O'
    }
  }
}

export { Square, State }