<script setup>
import { useAttractionStore } from "@/stores/attraction"
import { useModalStore } from '@/stores/modal';
import { usePlanStore } from '@/stores/plan';
import AttractionRowListItem from '@/components/attractions/item/AttractionRowListItem.vue';
import draggable from "vuedraggable";

defineProps({
  mode: String,
  num: Number,
  scheduleByDay: Array,
})

const attractionStore = useAttractionStore()
const modalStore = useModalStore()
const planStore = usePlanStore()

</script>

<template>
    <div v-if="mode==='Search'" class="flex flex-col overflow-y-auto scrollbar-thin mt-1 h-[37rem]">
      <draggable
        v-model="attractionStore.attractions"
        :group="{name: 'attr-plan', pull: 'clone', put: false}"
        :sort="false"
        item-key="id"
      >
      <template #item="{element}">
        <AttractionRowListItem :mode="mode" @click="modalStore.openModal('Attraction', element)" :key="element.id" :attraction="element"></AttractionRowListItem>
      </template>
      </draggable>
    </div>
    <div v-else-if="mode==='DayPlan'">
      <h2 class="text-xl font-bold text-black ml-8 mt-8">Day {{ num }}</h2>
      <div class="flex flex-col p-2 ml-8 mr-10 mt-3 border border-[4px] border-[#CABFFD] rounded-xl overflow-y-auto scrollbar-thin shadow-lg min-h-[8rem] max-h-[27.3rem]">
        <draggable
          v-model="planStore.schedules[num-1].attractions"
          group="attr-plan"
          item-key="id"
          class="space-y-2 min-h-[8rem] max-h-[25rem]"
        >
          <template #item="{element, index}">
            <AttractionRowListItem :mode="mode" :key="element.id" :attraction="element"
            :num="num" :index="index" class="rounded-lg"></AttractionRowListItem>
          </template>
        </draggable>
      </div>
    </div>
    <div v-else-if="mode==='DayPlanText'" class="overflow-y-auto scrollbar-thin h-[10rem]">
      <AttractionRowListItem v-for="element in scheduleByDay" mode="DayPlanText" :key="element.id" :attraction="element" class="rounded-lg"></AttractionRowListItem>
    </div>
</template>

<style scoped>

</style>
