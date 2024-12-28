<script setup>
const address = "서울 강남구"
</script>

<template>
    <div class="relative">
        <div class="block w-[24rem] h-36 p-3 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-6">
            <p class="text-lg font-bold">
                <span class="text-darkLimeGreen">{{ address }}</span>
                <span> 주간 날씨</span>
            </p>
        </div>
    </div>
</template>

<style scoped>

</style>