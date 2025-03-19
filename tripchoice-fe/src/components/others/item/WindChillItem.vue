<script setup>
import { computed } from "vue"
import { useWeatherStore } from "@/stores/weather"
import sun from "@/assets/icon/sun.png";
import wind from "@/assets/icon/wind.png";

const weatherStore = useWeatherStore();
const windChillImg = computed(() => {
  return weatherStore.getWindChillBoolean() ? sun : wind;
})
</script>

<template>
    <div :class="[
    'absolute top-40 left-8 block max-w-sm w-80 h-24 p-4 border border-gray-200 rounded-lg shadow dark:border-gray-700 mt-8',
    weatherStore.getWindChillBoolean() ? 'bg-red-500' : 'bg-blue-500'
  ]">
        <div class="flex">
            <img :src="windChillImg" class="w-8 h-8"/>
            <span class="font-bold text-white ml-3">체감온도</span>
            <span class="ml-auto text-white font-semibold text-3xl">{{ weatherStore.getWindChill() }}°C</span>
        </div>
        <div class="text-white text-xs mt-1">
            <p class="font-medium">풍속 {{ weatherStore.nowWeather.wsd }}m/s</p>
            <p class="font-medium">습도 {{ weatherStore.nowWeather.reh }}%</p>
        </div>
    </div>
</template>

<style scoped>

</style>
