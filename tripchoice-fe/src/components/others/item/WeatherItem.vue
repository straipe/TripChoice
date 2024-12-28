<script setup>
import cloud from "@/assets/icon/cloud.png"
import darkCloud from "@/assets/icon/cloud-dark.png"

defineProps({
    type:String,
})
</script>

<template>
    <div v-if="type==='Bar'" class="absolute top-12 left-8 block max-w-sm w-80 h-24 p-4 bg-gray-400 border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-8">
        <div class="flex">
            <img :src="cloud" class="w-8 h-8"/>
            <span class="font-bold text-white ml-3">구름많음</span>
            <span class="ml-auto text-white font-semibold text-3xl">20°C</span>
        </div>
        <div class="text-white text-xs mt-1">
            <p class="font-medium">최저 15°C | 최고 25°C</p>
            <p class="font-medium">강수확률 60%</p>
        </div>
    </div>
    <div v-if="type==='Text'" class="absolute block w-full h-24 p-4 mt-4">
        <div class="flex justify-end">
            <span class="font-bold text-black mt-1">구름많음</span>
            <img :src="darkCloud" class="w-8 h-8 ml-2"/>
            <span class="text-black font-semibold text-xl ml-2 mr-3">20°C</span>
        </div>
        <div class="flex flex-col items-end text-black text-xs mt-1 mr-3">
            <p class="font-medium">최저 15°C | 최고 25°C</p>
            <p class="font-medium">강수확률 60%</p>
        </div>
    </div>
</template>

<style scoped>

</style>