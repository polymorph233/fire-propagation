<script setup lang="ts">

import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";

const router = useRouter()
const route = useRoute()

const goBack = () => {
  router.back()
}

const coos = ref<number[][]>([])
const height = ref(0)
const width = ref(0)

onMounted(() => {
  const _coos = <string> route.query.coos
  const _height: number = Number.parseInt(<string>route.query.height)
  const _width: number = Number.parseInt(<string>route.query.height)

  coos.value = checkValidOrSetToEmpty(_coos, _height, _width)
  height.value = _height
  width.value = _width
})

/**
 * Checks if a given string of coordinates is valid and convert it to valid coordinate list
 * We only consider int numbers from 0 to 60
 *
 * @param cooList
 * @param heightLimit
 * @param widthLimit
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
const isWhole = (value: string) => {
  return /^\d+$/.test(value)
}

</script>

<template>
  <div>Height: {{ height }}</div>
  <div>Width: {{ width }}</div>
  <div>Validated coos: {{ coos }}</div>
  <div>
    <button class="button" @click="goBack()">Back</button>
  </div>
</template>

<style scoped>

</style>