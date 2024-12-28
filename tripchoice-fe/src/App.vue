<script setup>
import { RouterView } from "vue-router";
import { useModalStore } from '@/stores/modal';
import VModal from "@/components/common/VModal.vue";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import VFooter from "./components/common/VFooter.vue";

const modalStore = useModalStore()

</script>

<template>
  <div>
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view class="mt-[87px]"></router-view>
    <VModal
      :mode="modalStore.modalMode"
      :title="modalStore.modalTitle"
      :isOpen="modalStore.isModalOpen"
      :content="modalStore.selectedContent"
      ></VModal>
      <VFooter v-if="$route.path !== '/attractions'"></VFooter>
  </div>
</template>

<style scoped>

</style>
