<script setup>
import VModalDetailItem from "@/components/common/VModalDetailItem.vue";
import AttractionModalFormItem from "@/components/attractions/item/AttractionModalFormItem.vue";
import UserLoginModalFormItem from "@/components/users/item/UserLoginModalFormItem.vue";
import PlanModalFormItem from "@/components/plans/item/PlanModalFormItem.vue";
import PlanModalDetailItem from "@/components/plans/item/PlanModalDetailItem.vue";
import { useModalStore } from "@/stores/modal";

const modalStore = useModalStore()

defineProps({
    mode: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    isOpen: {
    type: Boolean,
    required: true,
    },
    content: {
        type: Object,
    },
})
</script>

<template>
    <div v-if="isOpen" tabindex="-1" class="fixed top-0 right-0 left-0 z-50 flex justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] h-full bg-opacity-50 bg-black">
        <div :class="{
          'max-w-6xl': mode==='OtherPlan' || mode==='MyPlan',
          'max-w-3xl': mode==='HotPlaceRegist',
          'max-w-xl': mode==='Attraction' || mode==='HotPlace' || mode==='PlanWrite',
          'max-w-md': mode==='Login'
        }" class="relative p-4 w-full max-h-full">
            <!-- Modal content -->
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <!-- Modal header -->
                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                  <div class="flex flex-row space-x-3">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white ml-5">
                        {{ title }}
                    </h3>
                    <span v-if="mode==='HotPlace' || mode==='Attraction'" class="text-sm font-semibold text-gray-400 mt-1">{{ content.contentType }}</span>
                  </div>


                  <button type="button" @click="modalStore.closeModal" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
                      <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                      </svg>
                      <span class="sr-only">Close modal</span>
                  </button>
                </div>
                <!-- Modal body -->
                <div class="p-4 md:p-5 overflow-y-auto scrollbar-thin max-h-[85vh]">
                    <!-- Login -->
                    <UserLoginModalFormItem v-if="mode==='Login'"></UserLoginModalFormItem>
                    <!-- Attraction, HotPlace, Plan -->
                    <VModalDetailItem v-else-if="mode==='Attraction' || mode==='HotPlace'" :mode="mode" :content="content"></VModalDetailItem>
                    <!-- HotPlaceRegist -->
                    <AttractionModalFormItem v-else-if="mode==='HotPlaceRegist'"></AttractionModalFormItem>
                    <!-- PlanRegist -->
                    <PlanModalFormItem v-else-if="mode==='PlanWrite'"></PlanModalFormItem>
                    <!-- PlanDetail -->
                    <PlanModalDetailItem v-else-if="mode==='MyPlan' || mode==='OtherPlan'" :mode="mode" :content="content"></PlanModalDetailItem>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>
