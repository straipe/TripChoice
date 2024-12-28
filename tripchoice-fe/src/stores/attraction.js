import { ref, computed } from "vue";
import { defineStore } from "pinia";

import noImage from "@/assets/images/no_image.png";

export const useAttractionStore = defineStore("attraction", () => {
  const attractions = ref([]);
  const top4Attractions = ref([]);
  const loading = ref(true);

  let id = 0;
  for (let i = 0; i < 4; i++) {
    attractions.value.push({
      id: id++,
      title: "관광지" + id,
      category: "문화시설",
      address: "서울시 강남구 역삼동",
      latitude: 37.501286,
      longitude: 127.0396029,
      imgUrl: noImage,
    });
  }

  const categories = [
    { text: "카테고리", value: "" },
    { text: "전체", value: "0" },
    { text: "관광지", value: "12" },
    { text: "문화시설", value: "14" },
    { text: "축제공연행사", value: "15" },
    { text: "여행코스", value: "25" },
    { text: "레포츠", value: "28" },
    { text: "숙박", value: "32" },
    { text: "쇼핑", value: "38" },
    { text: "음식점", value: "39" },
  ];

  const sidos = [
    { text: "시/도", value: "" },
    { text: "전체", value: "0" },
    { text: "서울", value: "1" },
    { text: "인천", value: "2" },
    { text: "대전", value: "3" },
    { text: "대구", value: "4" },
    { text: "광주", value: "5" },
    { text: "부산", value: "6" },
    { text: "울산", value: "7" },
    { text: "세종", value: "8" },
    { text: "경기", value: "31" },
    { text: "강원", value: "32" },
    { text: "충북", value: "33" },
    { text: "충남", value: "34" },
    { text: "경북", value: "35" },
    { text: "경남", value: "36" },
    { text: "전북", value: "37" },
    { text: "전남", value: "38" },
    { text: "제주", value: "39" },
  ];

  const guguns = ref([
    { text: "구/군", value: "" },
    { text: "전체", value: "0" },
  ]);

  const dongs = ref([{ text: "동", value: "" }]);

  function getTextByValue(arr, value) {
    const item = arr.find((item) => item.value === value);
    return item ? item.text : null;
  }

  return {
    attractions,
    top4Attractions,
    categories,
    sidos,
    guguns,
    dongs,
    loading,
    getTextByValue,
  };
});
