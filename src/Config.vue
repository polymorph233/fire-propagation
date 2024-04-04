<script setup lang="ts">

import {useRouter} from "vue-router";
import {Ref, ref} from "vue";

let route = useRouter()

let width = ref(5)
let height = ref(5)

let cooInFreeForm = ref("")

const checkValidOrSetToDefault = (field: Ref<number>, backing: number) => {
  if (field.value < 0 || field.value > 60) {
    return backing
  } else {
    return field.value
  }
}

const buildAndNavigateToGame = () => {
  let validHeight = checkValidOrSetToDefault(height, 5)
  let validWidth = checkValidOrSetToDefault(width, 5)

  route.push({ name: 'game-page', query: { height: validHeight, width: validWidth, coos: cooInFreeForm.value } })
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