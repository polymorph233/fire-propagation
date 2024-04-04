import { expect, test } from "vitest"
import { Game } from "../core/Game"



test("case where fire always propagate", () => {
  const initFires = [[1, 2]]
  const game = new Game(5, 5, 1.0, initFires)

  const step1 = "OOOOO\nOOXOO\nOOOOO\nOOOOO\nOOOOO"
  expect(game.printAndAdvance()).toBe(step1)
  const step2 = "OOXOO\nOX_XO\nOOXOO\nOOOOO\nOOOOO"
  expect(game.printAndAdvance()).toBe(step2)
  const step3 = "OX_XO\nX___X\nOX_XO\nOOXOO\nOOOOO"
  expect(game.printAndAdvance()).toBe(step3)
})

test("game should finish in two turns", () => {
  const initFires = [[1, 1]]
  const game = new Game(2, 2, 1.0, initFires)

  game.nextTurn()
  expect(game.isGameFinished()).toBe(false)
  game.nextTurn()
  expect(game.isGameFinished()).toBe(false)
  game.nextTurn()
  expect(game.isGameFinished()).toBe(true)
})