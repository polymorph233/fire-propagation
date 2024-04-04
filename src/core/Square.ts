

enum State {
  TREE, ON_FIRE, ASHES
}

class Square{

  private readonly state: State

  constructor(state: State) {
    this.state = state
  }
  getState() {
    return this.state;
  }

  toString() {
    switch (this.state) {
      case State.ASHES: return '_'
      case State.ON_FIRE: return 'X'
      case State.TREE: return 'O'
    }
  }
}

export { Square, State }