<script setup>
import { useWeatherStore } from "@/stores/weather"

defineProps({
  tags: Object
})

const weatherStore = useWeatherStore();

</script>

<template>
    <div class="relative">
      <div class="block w-[24rem] h-[17rem] p-3 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-6">
          <p class="text-lg font-bold">
              <span class="text-darkLimeGreen">{{ weatherStore.address }}</span>
              <span> 관련 태그 추천</span>
          </p>
          <div class="mt-5 flex flex-wrap justify-center gap-4">
            <span v-for="tag in tags" class="px-4 py-2 bg-blue-100 text-blue-800 rounded-full text-sm font-medium hover:bg-blue-200 transition duration-200">
              {{ tag }}
            </span>
          </div>
      </div>
    </div>
</template>

<style scoped>
</style>
