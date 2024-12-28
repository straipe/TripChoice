<script setup>
import wind from "@/assets/icon/wind.png"
</script>

<template>
    <div class="absolute top-40 left-8 block max-w-sm w-80 h-24 p-4 bg-[#38bdf8] border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-8">
        <div class="flex">
            <img :src="wind" class="w-8 h-8"/>
            <span class="font-bold text-white ml-3">체감온도</span>
            <span class="ml-auto text-white font-semibold text-3xl">15°C</span>
        </div>
        <div class="text-white text-xs mt-1">
            <p class="font-medium">풍속 3m/s</p>
            <p class="font-medium">습도 60%</p>
        </div>
    </div>
</template>

<style scoped>

</style>