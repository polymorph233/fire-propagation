<script setup lang="ts">

import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {Game} from "@/core/Game";

// Import the vue-router facilities
const router = useRouter()
const route = useRoute()

/**
 * Navigate back to last page
 */
const goBack = () => {
  router.back()
}

// Set up private fields of the `GameDisplay` page that holds sources of the inputs
const coos = ref<number[][]>([])
const height = ref(0)
const width = ref(0)
const rate = ref(0.5)

const count = ref(0)

// Set up private instance of a game that would hold the Game object
const game = ref<Game>()

// Set up a string source where the page will be updated from, and it will be updated through
// the game instance per turn
const renderSource = ref<string>()

// Fill in private fields and fire the game running logic
onMounted(() => {

  // Convert query params into typed variables, we have omitted checks here since we suppose
  // that `Config` page always provide valid values
  const _coos = <string> route.query.coos
  const _height: number = Number.parseInt(<string>route.query.height)
  const _width: number = Number.parseInt(<string>route.query.height)
  const _rate: number = Number.parseFloat(<string>route.query.rate)

  // Fill in ref fields with these variables
  coos.value = checkValidOrSetToEmpty(_coos, _height, _width)
  height.value = _height
  width.value = _width
  rate.value = _rate

  // Fill in count field
  count.value = _width * _height

  // Set up the game object
  game.value = new Game(height.value, width.value, rate.value, coos.value)

  // Set up the render source to represent a board where all squares are trees
  renderSource.value = "O".repeat(_height * _width)

  // Run the game and get all inputs at once
  const allSteps = getAllGameSteps()

  // Render the game where each steps are interpolated by 0.5 secs
  renderGameSteps(allSteps)
})

/**
 * Checks if a given string of coordinates is valid and convert it to valid coordinate list,
 * it will give an empty list if it's invalid
 *
 * We only consider int numbers from 0 to 60
 *
 * @param cooList the coordinate array to check, in form of a string
 * @param heightLimit the height of the board
 * @param widthLimit the width of the board
 */
const checkValidOrSetToEmpty = (cooList: string, heightLimit: number, widthLimit: number) => {
  let coos = cooList.split(",").map(s => s.trim())
  let pairs = coos.map(s => s.split("-").map(s => s.trim()))
  let res: number[][] = []

  for (let pair of pairs) {
    if (pair.length !== 2) {
      return []
    } else {
      if (isWhole(pair[0]) && isWhole(pair[1])) {
        let curr = pair.map(e => Number.parseInt(e))

        if (curr[0] < heightLimit && curr[1] < widthLimit) {
          res.push(curr)
        } else {
          return []
        }
      } else {
        return []
      }
    }
  }
  return res
}

// Source: https://stackoverflow.com/questions/175739/how-can-i-check-if-a-string-is-a-valid-number
/**
 * Check if a string represents a positive int number
 * @param value
 */
const isWhole = (value: string) => {
  return /^\d+$/.test(value)
}

/**
 * Get all steps of the game
 * @return an array of string each represents a state of the game, in a single-line manner
 */
const getAllGameSteps = (): string[] => {
  let ret = []
  while (!game.value.isGameFinished()) {
    ret.push(game.value.produceStateSequence())
    game.value.nextTurn()
  }
  ret.push(game.value.produceStateSequence())
  return ret
}

/**
 * Render all steps of the game in a speed of 0.5 sec between frames
 * @param steps all steps of the game
 */
const renderGameSteps = (steps: string[]) => {
  if (steps.length === 0) {
    return
  } else {
    const curr = steps.shift()
    console.log(curr)
    renderSource.value = curr
    setTimeout(() => {
      renderGameSteps(steps)
    }, 500)
  }
}

</script>

<template>
  <div>Height: {{ height }}</div>
  <div>Width: {{ width }}</div>
  <div>Rate: {{ rate }}</div>
  <div>Validated coos: {{ coos }}</div>

  <div class="grid">
    <div v-for="i in (count)" :key="i" class="square">
      <div v-if="renderSource[i-1] === 'X'" class="fire">
        {{renderSource[i-1]}}
      </div>
      <div v-else-if="renderSource[i-1] === 'O'" class="tree">
        {{renderSource[i-1]}}
      </div>
      <div v-else class="ashes">
        {{renderSource[i-1]}}
      </div>
    </div>
  </div>

  <div>
    <button class="button" @click="goBack()">Back</button>
  </div>
</template>

<style scoped>

.grid {
  width: 800px;
  height: 800px;
  padding: 5%;

  display: grid;
  grid-column-gap: 5px;
  grid-row-gap: 5px;
  grid-template-columns: repeat(v-bind(width), auto);
}

.square {
  width: 100%;
  height: 100%;
  margin: 10px;
  border: 1px solid black;
  position: relative;
}

.fire {
  height: 100%;
  width: 100%;
  background-color: red;
}

.tree {
  height: 100%;
  width: 100%;
  background-color: green;
}

.ashes {
  height: 100%;
  width: 100%;
  background-color: gray;
}
</style>