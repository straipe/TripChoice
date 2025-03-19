<script setup>
import WeatherListItem from "@/components/others/item/WeatherListItem.vue"

import { onMounted } from "vue"
import { listWeather } from "@/api/weather"
import { useWeatherStore } from "@/stores/weather"

const weatherStore = useWeatherStore();

onMounted(() => getWeatherList())

const getWeatherList = () =>{
  listWeather(
    { X:"61", Y:"125" },
    ({ data }) => {
      weatherStore.weatherList = data;
    },
    (error) => {
      console.log(error)
    }
  )
}

</script>

<template>
    <div class="relative">
        <div class="block w-[24rem] h-36 p-3 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-6">
            <p class="text-lg font-bold">
                <span class="text-darkLimeGreen">{{ weatherStore.address }}</span>
                <span> 시간별 날씨</span>
            </p>
            <div class="flex justify-evenly mt-2">
              <WeatherListItem v-for="(weather, idx) in weatherStore.weatherList" :weather="weather" :idx="idx"/>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>
