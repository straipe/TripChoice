<script setup>
import AttractionRowList from '@/components/attractions/AttractionRowList.vue';

defineProps({
  scheduleByDay: Array,
  num:Number,
})

</script>

<template>
  <li class="mb-10 ms-4">
      <div class="absolute w-3 h-3 bg-gray-200 rounded-full mt-1.5 -start-1.5 border border-white dark:border-gray-900 dark:bg-gray-700"></div>
      <time class="mb-1 text-sm font-normal leading-none text-gray-400 dark:text-gray-500">{{ 'Day' + num }}</time>
      <AttractionRowList mode="DayPlanText" :scheduleByDay="scheduleByDay"></AttractionRowList>
  </li>
</template>

<style scoped>

</style>
