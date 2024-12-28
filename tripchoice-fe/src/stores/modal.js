import { ref } from "vue";
import { defineStore } from "pinia";

export const useModalStore = defineStore("modal", () => {
  const modalMode = ref("");
  const modalTitle = ref("");
  const isModalOpen = ref(false);
  const selectedContent = ref({});

  const openModal = (mode, content) => {
    modalMode.value = mode;
    if (mode !== "Login" && mode !== "HotPlaceRegist" && mode !== "PlanWrite")
      modalTitle.value = content.title;
    else if (mode === "Login") modalTitle.value = "로그인";
    else if (mode === "HotPlaceRegist") modalTitle.value = "HotPlace 등록";
    else if (mode === "PlanWrite") modalTitle.value = "일정 개요 작성";
    selectedContent.value = content;
    isModalOpen.value = true;
  };

  const closeModal = () => {
    isModalOpen.value = false;
  };

  return {
    modalMode,
    modalTitle,
    isModalOpen,
    selectedContent,
    openModal,
    closeModal,
  };
});
