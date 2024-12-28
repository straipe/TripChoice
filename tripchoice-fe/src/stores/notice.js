import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useNoticeStore = defineStore("notice", () => { 
    const notices = ref([])
    return { notices }
})