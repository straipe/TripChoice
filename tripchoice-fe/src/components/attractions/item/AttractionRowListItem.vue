<script setup>

import noImage from "@/assets/images/no_image.png"
import { usePlanStore } from "@/stores/plan";

defineProps({
  attraction: Object,
  index: Number,
  num: Number,
  mode: String,
})

const planStore = usePlanStore()

</script>

<template>
  <div v-if="mode==='Search'" class="flex flex-row items-center bg-white border border-gray-200 shadow md:max-w-xl hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700">
    <img class="object-cover w-[8rem] rounded-md h-[8rem]" :src="attraction.imgUrl !== '' ? attraction.imgUrl : noImage" alt="">
    <div class="flex flex-col justify-between p-4 leading-normal">
        <h5 class="mb-2 text-xl font-bold tracking-tight text-gray-900 dark:text-white">{{ attraction.title }}</h5>
        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">{{ attraction.address }}</p>
    </div>
  </div>
  <div v-else-if="mode==='DayPlan'" class="flex flex-row items-center bg-white border border-gray-200 shadow hover:bg-gray-100 my-3">
    <img class="object-cover w-[4rem] rounded-t-lg h-auto md:rounded-none md:rounded-s-lg" :src="attraction.imgUrl !== '' ? attraction.imgUrl : noImage" alt="">
    <div class="flex flex-col justify-between p-2 pr-3 leading-normal">
        <h5 class="mb-2 text-md font-bold tracking-tight text-gray-900 dark:text-white">{{ attraction.title }}</h5>
        <p class="font-normal text-gray-700 dark:text-gray-400">{{ attraction.address }}</p>
    </div>
    <button type="button" @click="planStore.popAttraction(num, index)" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center mb-2 dark:hover:bg-gray-600 dark:hover:text-white">
        <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
        </svg>
    </button>
  </div>
  <li v-else-if="mode==='DayPlanText'">
    <h3 class="text-lg font-semibold text-gray-900 dark:text-white">{{ attraction.title }}</h3>
    <p class="text-base font-normal text-gray-500 dark:text-gray-400">{{ attraction.address }}</p>
  </li>
</template>

<style scoped>

</style>
