<script setup>
defineProps({
  name: String,
  attractions: Object,
})
import AttractionCardListItem from './item/AttractionCardListItem.vue';
import { useModalStore } from '@/stores/modal';

const modalStore = useModalStore()
</script>

<template>
  <div class="mb-8">
    <div v-if="name==='Reduction'">
      <div class="grid md:grid-cols-3 sm:grid-cols-2 gap-4">
        <div v-for="(attraction, index) in attractions" :key="index" class="mt-3 flex-shrink-0 w-[16rem] h-60 rounded-lg overflow-hidden shadow-lg transition-transform duration-300 ease-in-out transform hover:scale-105">
          <AttractionCardListItem :name="name"  @click="modalStore.openModal('Attraction', attraction)" :attraction="attraction"></AttractionCardListItem>
        </div>
      </div>
    </div>
    <div v-else class="flex justify-center overflow-hidden items-center space-x-4 py-4 px-2">
      <div v-for="(attraction, index) in attractions" :key="index" class="flex-shrink-0 w-72 h-[20rem] rounded-lg overflow-hidden shadow-lg transition-transform duration-300 ease-in-out transform hover:scale-105">
        <AttractionCardListItem @click="modalStore.openModal('Attraction', attraction)" :attraction="attraction"></AttractionCardListItem>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
