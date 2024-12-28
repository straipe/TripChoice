<script setup>
import { ref } from 'vue'
import VSelect from "@/components/common/VSelect.vue";
import { useAttractionStore } from "@/stores/attraction"

defineProps({
  searchByLocation: {
    type: Boolean,
    default: true
  },
  length: {
    type: String,
    default: 'Long'
  }
})

const emit = defineEmits(["onAddressSelect", "onRadiusSelect"])
const attractionStore = useAttractionStore()

const areaCode = ref("")
const siGuGunCode = ref("")
const radius = ref(10)

const onSidoSelect = (code) => {
  areaCode.value = code
  console.log(areaCode.value + "번째 시/도 선택!!!");
  emit("onAddressSelect", areaCode.value, 'sido');
}

const onGuGunSelect = (code) => {
  siGuGunCode.value = code
  console.log(siGuGunCode.value + "번째 구/군 선택!!!");
  emit("onAddressSelect", siGuGunCode.value, 'gugun');
}

const onRadiusSelect = () => {
  console.log(radius.value + "km 선택");
  emit("onRadiusSelect", radius.value);
}

</script>

<template>
  <div v-if="length==='Long'" class="col-span-3">
    <div v-if="searchByLocation" class="relative ml-3 w-[95%]">
      <span class="font-bold text-xs text-gray-500 dark:text-gray-400 mr-2">거리</span>
      <label for="labels-range-input" class="sr-only">distance range</label>
      <input id="labels-range-input" @change="onRadiusSelect" v-model="radius" type="range" value="10" min="0" max="30" class="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer dark:bg-gray-700">
      <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute top-12 start-0 -bottom-6">0 km</span>
      <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute top-12 start-1/3 -translate-x-1/2 rtl:translate-x-1/2 -bottom-6">10 km</span>
      <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute top-12 start-2/3 -translate-x-1/2 rtl:translate-x-1/2 -bottom-6">20 km</span>
      <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute top-12 end-0 -bottom-6">30 km</span>
    </div>
    <div v-else class="relative ml-3 w-[95%]">
      <span class="font-bold text-xs text-gray-500 dark:text-gray-400 mr-2">주소</span>
      <div class="flex justify-evenly space-x-3">
        <VSelect type="Box" :select-option="attractionStore.sidos" @on-key-select="onSidoSelect"></VSelect>
        <VSelect type="Box" :select-option="attractionStore.guguns" @on-key-select="onGuGunSelect"></VSelect>
        <VSelect type="Box" :select-option="attractionStore.dongs"></VSelect>
      </div>
    </div>
  </div>
  <div v-if="length==='Short'" class="block relative w-[26rem] h-[6rem] p-2">
    <div v-if="searchByLocation" class="relative mt-2">
      <label for="labels-range-input" class="font-bold text-sm ml-5">거리</label>
        <div class="relative ml-4 w-[23rem]">
        <input id="labels-range-input" @change="onRadiusSelect" v-model="radius" type="range" value="10" min="0" max="30" class="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer dark:bg-gray-700">
        <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute start-0 -bottom-6">0 km</span>
        <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute start-1/3 -translate-x-1/2 rtl:translate-x-1/2 -bottom-6">10 km</span>
        <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute start-2/3 -translate-x-1/2 rtl:translate-x-1/2 -bottom-6">20 km</span>
        <span class="font-bold text-sm text-gray-500 dark:text-gray-400 absolute end-0 -bottom-6">30 km</span>
      </div>
    </div>
    <div v-else class="mt-2">
      <label for="underline_select" class="font-bold text-sm ml-5">주소</label>
      <div class="flex justify-evenly">
        <VSelect type="Line" :select-option="attractionStore.sidos" @on-key-select="onSidoSelect"></VSelect>
        <VSelect type="Line" :select-option="attractionStore.guguns" @on-key-select="onGuGunSelect"></VSelect>
        <VSelect type="Line" :select-option="attractionStore.dongs"></VSelect>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
