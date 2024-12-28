<script setup>
import { useModalStore } from '@/stores/modal'
import { usePlanStore } from '@/stores/plan';
const modalStore = useModalStore()
const planStore = usePlanStore()
defineProps({
    type: String,
    title: String,
})
const emit = defineEmits(['submit'])
const onClick = () => {
  console.log("클릭!!!")
  emit("submit")
}
</script>

<template>
    <div v-if="type=='Login' || type=='PlanWrite'">
        <button type="button" @click="modalStore.openModal(type)"
        :class="{
          'w-[calc(100%-40px)]': type==='PlanWrite'
        }"
        class="text-white font-bold bg-limeGreen hover:bg-darkLimeGreen focus:ring-darkLimeGreen focus:outline-none font-bold rounded-lg text-sm px-4 py-2 text-center">
            {{ title }}
        </button>
    </div>
    <div v-else-if="type=='Map'">
        <RouterLink :to="{'name':'attractions'}" @click="planStore.isOpen = false" class="text-darkLimeGreen font-bold bg-white focus:ring-darkLimeGreen focus:outline-none hover:bg-gray-50 font-bold rounded-lg text-sm px-4 py-2 text-center border border-darkLimeGreen">
            {{ title }}
        </RouterLink>
    </div>
    <div v-else>
        <button v-if="type==='Join'" type="submit" class="w-full h-[2.5rem] text-white font-bold bg-limeGreen hover:bg-darkLimeGreen focus:ring-darkLimeGreen focus:outline-none font-bold rounded-lg text-sm px-4 py-2 text-center">
            {{ title }}
        </button>
        <button v-else-if="type==='Modify'" type="submit" class="w-full h-[2.5rem] text-white font-bold bg-limeGreen hover:bg-darkLimeGreen focus:ring-darkLimeGreen focus:outline-none font-bold rounded-lg text-sm px-4 py-2 text-center">
            {{ title }}
        </button>
        <button v-else-if="type==='Search'" type="submit" class="text-white absolute end-2.5 bottom-2.5 font-bold bg-limeGreen hover:bg-darkLimeGreen focus:ring-darkLimeGreen focus:outline-none font-bold rounded-lg text-sm px-4 py-2 text-center">
            {{ title }}
        </button>
        <button v-else-if="type==='Code'" type="submit" class="text-darkLimeGreen font-bold bg-white focus:ring-darkLimeGreen focus:outline-none hover:bg-gray-50 font-bold rounded-lg text-sm px-4 py-2 text-center border border-darkLimeGreen">
            {{ title }}
        </button>
        <button v-else-if="type==='Submit'" @click="onClick" type="submit" class="text-darkLimeGreen font-bold bg-white focus:ring-darkLimeGreen focus:outline-none hover:bg-gray-50 font-bold rounded-lg text-sm px-4 py-2 text-center border border-darkLimeGreen">
            {{ title }}
        </button>
        <button v-else-if="type==='Submit2'" type="submit" class="w-full text-white bg-limeGreen hover:bg-darkLimeGreen focus:ring-4 focus:outline-none font-bold rounded-lg text-sm px-5 py-2.5 text-center">
            {{ title }}
        </button>
        <button v-else-if="type==='Move'" type="button" class="text-darkLimeGreen font-bold bg-white focus:ring-darkLimeGreen focus:outline-none hover:bg-gray-50 font-bold rounded-lg text-sm px-4 py-2 text-center border border-darkLimeGreen">
            {{ title }}
        </button>
        <button v-else-if="type==='Delete'" type="button" class="w-full text-darkLimeGreen font-bold bg-white focus:ring-darkLimeGreen focus:outline-none hover:bg-gray-50 font-bold rounded-lg text-sm px-4 py-2 text-center border border-darkLimeGreen">
            {{ title }}
        </button>
    </div>

</template>

<style scoped>

</style>
