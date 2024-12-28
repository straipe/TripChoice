<script setup>
import { ref, onMounted, watch } from "vue"
const { defaultOption, selectOption } = defineProps({
    type: String,
    selectOption: Object,
    attr: String,
    defaultOption: String,
})

const emit = defineEmits(["onKeySelect"])
const key = ref("")

onMounted(()=>{
  if(defaultOption) key.value = defaultOption
})

const onSelect = () => {
  console.log(key.value + "선택!!!");
  emit("onKeySelect", key.value);
}

const resetSelect = () => {
  key.value = ''
  emit("onKeySelect", key.value);
}
watch(() => selectOption, resetSelect)

</script>

<template>
    <select v-if="type==='Line'" @click="onSelect" v-model="key" id="underline_select" class="block py-2.5 px-0 w-1/4 text-sm text-gray-500 bg-transparent border-0 border-b-2 border-gray-200 appearance-none dark:text-gray-400 dark:border-gray-700 focus:outline-none focus:ring-0 focus:border-gray-200 peer">
        <option v-for="option in selectOption" :key="option.value" :value="option.value" :disabled="option.value === '' ? true : false">{{ option.text }}</option>
    </select>
    <select v-if="type==='Box'" @click="onSelect" v-model="key" id="box_select"
        :class='[attr, "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"]'>
        <option v-for="option in selectOption" :key="option.value"
        :value="option.value"
        :disabled="option.value === ''">{{ option.text }}</option>
    </select>
</template>

<style scoped>

</style>
