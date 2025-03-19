<script setup>
import { useWeatherStore } from "@/stores/weather";

defineProps({
    weather: Object,
    idx: Number,
})

const weatherStore = useWeatherStore();

</script>

<template>
  <div>
    <p class="text-sm text-center">{{ weather.tmp }}°C</p>
    <img :src="weatherStore.getWeatherImg(1, weather.sky, weather.pty)" class="w-8 h-8 mt-1"/>
    <p class="mt-3 text-xs text-center">{{ idx<2 ? "0" : "" }}{{ 4*(idx+1) }}시</p>
  </div>
</template>

<style scoped>

</style>
