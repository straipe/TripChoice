<script setup>
import WeatherItem from "@/components/others/item/WeatherItem.vue"
import WindChillItem from "@/components/others/item/WindChillItem.vue"
const address = "서울 강남구"
defineProps({
    type: String,
})
</script>

<template>
    <div class="relative">
        <div v-if="type==='Bar'" class="block w-[24rem] h-72 p-3 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-6">
            <p class="text-lg font-bold">
                <span class="text-darkLimeGreen">{{ address }}</span>
                <span> 오늘 날씨</span>
            </p>
            <WeatherItem :type="type"></WeatherItem>
            <WindChillItem :type="type"></WindChillItem>
        </div>
        <div v-else-if="type==='Text'" class="block w-full h-24">
            <p class="absolute text-2xl font-bold text-black ml-8 mt-8">
                <span>{{ address }}</span>
            </p>
            <WeatherItem :type="type"></WeatherItem>
        </div>
    </div>

</template>

<style scoped>

</style>