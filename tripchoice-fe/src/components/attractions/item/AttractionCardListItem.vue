<script setup>
import noImage from "@/assets/images/no_image.png"
defineProps({
  name: String,
  attraction: Object,
})
</script>

<template>
  <div v-if="name==='Reduction'" class="h-60 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <img :src="attraction.imgUrl ? attraction.imgUrl : noImage" :alt="attraction.title" class="rounded-t-lg w-full h-40">
      <div class="p-3">
        <hr/>
        <h5 class="mt-2 mb-2 text-lg font-bold tracking-tight text-gray-900 dark:text-white">{{ attraction.title }}</h5>
      </div>
  </div>
  <div v-else class="h-[20rem] max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <img :src="attraction.imgUrl ? attraction.imgUrl : noImage" :alt="attraction.title" class="rounded-t-lg w-full h-[12rem]">
      <div class="p-2">
          <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 text-sm">{{ attraction.category }}</p>
          <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{{ attraction.title }}</h5>
          <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">{{ attraction.address }}</p>
      </div>
  </div>
</template>

<style scoped>

</style>
