<script setup>
import PlanCardListItem from "./item/PlanCardListItem.vue";
import { usePlanStore } from "@/stores/plan";
defineProps({
    mode: String,
    plans: Object
})
const planStore = usePlanStore()
</script>

<template>
    <div v-if="mode==='Main'" class="flex space-x-4 py-4 px-2 mb-8">
        <div v-for="(plan, index) in plans" :key="index" class="flex-shrink-0 w-72 h-[20rem] rounded-lg overflow-hidden shadow-lg transition-transform duration-300 ease-in-out transform hover:scale-105">
            <PlanCardListItem :plan="plan" mode="OtherPlan" :index="index"></PlanCardListItem>
        </div>
    </div>
    <div v-else-if="mode==='OtherPlan'" class="grid grid-cols-2 md: grid-cols-3 gap-4 mt-3 w-full">
        <div class="flex items-center justify-center h-[20rem]">
          <RouterLink :to="{'name':'attractions'}" @click="planStore.isOpen = true" class="flex flex-col w-full h-full items-center justify-center border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
              <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <p class="text-gray-400 text-5xl">+</p>
                  <p class="mt-5 mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">클릭</span>하여 Plan를 등록하세요.</p>
              </div>
          </RouterLink>
        </div>
        <div v-for="(plan, index) in plans" :key="index" class="flex-shrink-0 w-full h-[20rem] rounded-lg overflow-hidden shadow-lg transition-transform duration-300 ease-in-out transform hover:scale-105">
            <PlanCardListItem :plan="plan" mode="OtherPlan" :index="index"></PlanCardListItem>
        </div>
    </div>
    <div v-else-if="mode==='MyPlan'" class="grid grid-cols-2 md: grid-cols-3 gap-4 mt-3">
        <div class="flex items-center justify-center">
          <RouterLink :to="{'name':'attractions'}" @click="planStore.isOpen = true" class="flex flex-col w-full h-full items-center justify-center border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
              <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <p class="text-gray-400 text-5xl">+</p>
                  <p class="mt-5 mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">클릭</span>하여 Plan를 등록하세요.</p>
              </div>
          </RouterLink>
        </div>
        <div v-for="(plan, index) in plans" :key="index" class="flex-shrink-0 w-full h-[20rem] rounded-lg overflow-hidden shadow-lg transition-transform duration-300 ease-in-out transform hover:scale-105">
            <PlanCardListItem :plan="plan" mode="MyPlan" :index="index"></PlanCardListItem>
        </div>
    </div>
</template>

<style scoped>

</style>
