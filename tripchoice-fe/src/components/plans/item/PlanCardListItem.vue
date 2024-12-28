<script setup>
import { useModalStore } from "@/stores/modal";
import noImage from "@/assets/images/no_image.png"

defineProps({
    mode: String,
    index: Number,
    plan: Object,
})
const modalStore = useModalStore()
</script>

<template>
    <div class="relative max-w-sm h-full bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
        <img :src="!plan.imgUrl ? noImage : plan.imgUrl" :alt="plan.title" @click="modalStore.openModal(mode, plan)" class="rounded-t-lg w-full h-[12rem]">
        <div class="p-3">
            <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 text-sm">{{ plan.category }}</p>
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{{ plan.title }}</h5>
            <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">{{ plan.summary }}</p>
        </div>
    </div>
</template>

<style scoped>

</style>
