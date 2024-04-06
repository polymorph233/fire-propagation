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

  route.push({ name: 'game-page', query: { height: validHeight, width: validWidth, rate: validRate, coos: cooInFreeForm.value } })
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
    <div>Initial coordinates</div>
    <div>Please follow the format 1-2,3-4 where row comes first</div>
    <input class="input" type="text" v-model="cooInFreeForm" />
  </div>
  <div>
    <button class="button" @click="buildAndNavigateToGame()">Play</button>
  </div>
</template>

<style scoped>

</style>