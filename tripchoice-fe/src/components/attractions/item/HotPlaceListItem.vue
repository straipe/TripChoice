<script setup>
defineProps({
  image: String,
})
</script>

<template>
  <img class="h-[15rem] w-full rounded-lg" :src="image">
</template>

<style scoped>

</style>
