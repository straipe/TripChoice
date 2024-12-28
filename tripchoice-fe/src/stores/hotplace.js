import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useHotPlaceStore = defineStore("hotPlace", () => {
  const hotPlaces = ref([]);
  const isLoading = ref(true);

  return { hotPlaces, isLoading };
});
