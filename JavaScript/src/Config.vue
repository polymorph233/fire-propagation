<script setup lang="ts">

import {useRouter} from "vue-router";
import {Ref, ref} from "vue";

// Import the vue-router facilities
let route = useRouter()

// Set up private fields of the `Config` page that holds sources of the inputs
let width = ref(5)
let height = ref(5)
let propagationRate = ref(0.5)

let cooInFreeForm = ref("")

let cooList = ref<number[][]>([])
let cooY = ref(2)
let cooX = ref(2)

// Check if a field is valid, by given range and returns it, otherwise it would return to backing field
const checkValidOrSetToDefault = (field: Ref<number>, from: number, to: number, backing: number) => {
  if (field.value < from || field.value > to) {
    return backing
  } else {
    return field.value
  }
}

/**
 * Validate inputs and use them to navigate to game page
 */
const buildAndNavigateToGame = () => {
  let validHeight = checkValidOrSetToDefault(height, 0, 60, 5)
  let validWidth = checkValidOrSetToDefault(width, 0, 60, 5)
  let validRate = checkValidOrSetToDefault(propagationRate, 0.0, 1.0, 0.5)

  route.push({ name: 'game-page', query: {
    height: validHeight, width: validWidth, rate: validRate,
      coos: cooList.value.map(coo => `${coo[0]}-${coo[1]}`).join(',') } })
}

// Add a valid coordinate that's under the bound
// However, if the board size is changed, previous input may be invalid
const addCoordinate = () => {
  let cooYValue = cooY.value
  let cooXValue = cooX.value

  console.log(cooYValue)
  console.log(height)

  if (cooYValue < 0 || cooYValue >= height.value) {
    cooY.value = 2
    return
  }
  if (cooXValue < 0 || cooXValue >= width.value) {
    cooX.value = 2
    return
  }

  if (cooList.value.find(e => (e[0] === cooYValue && e[1] === cooXValue)) === undefined) {
    cooList.value.push([cooYValue, cooXValue])
  }
}

const removeAllCoordinate = () => {
  cooList.value = []
}

</script>

<template>
  <div>
    <div>Height</div>
    <input class="input" type="number" min=0 max=20  v-model="height" />
  </div>
  <div>
    <div>Width</div>
    <input class="input" type="number" min=0 max=20  v-model="width" />
  </div>
  <div>
    <div>Propagation rate</div>
    <input class="input" type="number" min=0 max=1 step="0.01"  v-model="propagationRate" />
  </div>
  <div>
    <div>Y coordinate of your new fire spot</div>
    <input class="input" type="number" min=0 max=20  v-model="cooY" />
  </div>
  <div>
    <div>X coordinate of your new fire spot</div>
    <input class="input" type="number" min=0 max=20  v-model="cooX" />
  </div>
  <div>
    <button class="button" @click="addCoordinate()">Add fire spot</button>
  </div>
  <div>
    <button class="button" @click="removeAllCoordinate()">Remove all fires</button>
  </div>
  <div>
    <div v-for="i in cooList.length" :key="i" class="coo">
      <div>{{cooList[i-1][0]}} - {{cooList[i-1][1]}}</div>
    </div>
  </div>
  <div>
    <button class="button" @click="buildAndNavigateToGame()">Play</button>
  </div>
</template>

<style scoped>

</style>