<script setup>
import { ref } from 'vue'
defineProps({
    value: String,
    text: String,
})

const emit = defineEmits(["onSelect2"])

const contentTypeId = ref("")

const onChange2 = () => {
  console.log(contentTypeId.value + "선택!!! 222");
  emit("onSelect2", contentTypeId.value);
}

</script>

<template>
    <div v-if="value!==''">
        <input :id="value" @change="onChange2" v-model="contentTypeId" name="category" type="radio" :value="value"
        :checked = "value === '0'"
        class="hidden peer">
        <label :for="value"
        :class="{
                'peer-checked:border-black' : value === '0',
                'peer-checked:border-[#F05252]' : value === '12',
                'peer-checked:border-[#E3A008]' : value === '14',
                'peer-checked:border-[#FACA15]' : value === '15',
                'peer-checked:border-[#31C48D]' : value === '25',
                'peer-checked:border-[#1C64F2]' : value === '28',
                'peer-checked:border-[#5145CD]' : value === '32',
                'peer-checked:border-[#7E3AF2]' : value === '38',
                'peer-checked:border-[#E74694]' : value === '39',

            }"
        class="bg-white shadow-lg border border-[3px] hover:bg-gray-100 rounded-full px-5 py-2.5 me-2 mb-2 font-bold text-sm text-gray-900 dark:text-gray-300">
            {{ text }}
        </label>
    </div>
</template>

<style scoped>

</style>
