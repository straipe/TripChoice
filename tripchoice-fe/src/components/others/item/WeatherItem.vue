<script setup>
import { computed } from "vue"
import { useWeatherStore } from "@/stores/weather"

defineProps({
    type:String,
})

const weatherStore = useWeatherStore()
const word = computed(() => {
  return weatherStore.nowWeather.pcp === "강수없음" ? "" : "강수량:"
})


</script>

<template>
    <div v-if="type==='Bar'" class="absolute top-12 left-8 block max-w-sm w-80 h-24 p-4 border border-gray-200 rounded-lg shadow mt-8"
    :class="{
                'bg-sky-400' : weatherStore.nowWeather.sky === 1,
                'bg-slate-400' : weatherStore.nowWeather.sky === 3,
                'bg-gray-500' : weatherStore.nowWeather.sky === 4,
            }">
        <div class="flex">
            <img :src="weatherStore.getWeatherImg(0, weatherStore.nowWeather.sky, weatherStore.nowWeather.pty)" class="w-8 h-8"/>
            <span class="font-bold text-white ml-3">{{ weatherStore.getWeatherName(weatherStore.nowWeather.sky, weatherStore.nowWeather.pty) }}</span>
            <span class="ml-auto text-white font-semibold text-3xl">{{ weatherStore.nowWeather.tmp }}°C</span>
        </div>
        <div class="text-white text-xs mt-1">
            <p class="font-medium">{{ word }} {{ weatherStore.nowWeather.pcp }}</p>
            <p class="font-medium">강수확률 {{ weatherStore.nowWeather.pop }}%</p>
        </div>
    </div>
    <div v-if="type==='Text'" class="absolute block w-full h-24 p-4 mt-4">
        <div class="flex justify-end">
            <span class="font-bold text-black mt-1">{{ weatherStore.getWeatherName(weatherStore.nowWeather.sky, weatherStore.nowWeather.pty) }}</span>
            <img :src="weatherStore.getWeatherImg(1, weatherStore.nowWeather.sky, weatherStore.nowWeather.pty)" class="w-8 h-8 ml-2"/>
            <span class="text-black font-semibold text-xl ml-2 mr-3">{{ weatherStore.nowWeather.tmp }}°C</span>
        </div>
        <div class="flex flex-col items-end text-black text-xs mt-1 mr-3">
            <p class="font-medium">{{ word }} {{ weatherStore.nowWeather.pcp }}</p>
            <p class="font-medium">강수확률 {{ weatherStore.nowWeather.pop }}%</p>
        </div>
    </div>
</template>

<style scoped>

</style>
