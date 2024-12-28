<script setup>
import VRadio from "@/components/common/VRadio.vue";
import VSelect from "@/components/common/VSelect.vue";
import { ref } from 'vue'
defineProps({
  type: String,
  inherit: String,
  categories:Object,
})

const emit = defineEmits(["onSelect1"])
const contentTypeId = ref('')

const onChange1 = (val) => {
  console.log(val + "선택!!! 111");
  contentTypeId.value = val
  emit("onSelect1", contentTypeId.value);
}

</script>

<template>
  <div v-if="type==='Radio'"
  :class="inherit"
  class="flex flex-wrap space-x-1 w-[57rem]">
    <VRadio v-for="category in categories" @onSelect2="onChange1" :key="category.value" :value="category.value" :text="category.text" />
  </div>
  <div v-if="type==='Select'">
    <VSelect type="Box" @onKeySelect="onChange1" :select-option="categories"></VSelect>
  </div>
</template>

<style scoped>

</style>
