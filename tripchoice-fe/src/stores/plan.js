import { ref, computed, watch } from "vue";
import { defineStore } from "pinia";

import leftImg from "@/assets/icon/left.png";
import rightImg from "@/assets/icon/right.png";

import planMarker1 from "@/assets/icon/planMarker1.png";
import planMarker2 from "@/assets/icon/planMarker2.png";
import planMarker3 from "@/assets/icon/planMarker3.png";

export const usePlanStore = defineStore("plan", () => {
  const plans = ref([]);
  const isLoading = ref(true);
  const plan = ref({
    id: 0,
    title: "",
    imgUrl: "",
    summary: "",
    schedules: [],
  });

  const schedules = ref([
    {
      attractions: [],
    },
  ]);

  const images = [
    {
      imageSrc: planMarker1,
      imageWidth: 48,
      imageHeight: 48,
    },
    {
      imageSrc: planMarker2,
      imageWidth: 48,
      imageHeight: 48,
    },
    {
      imageSrc: planMarker3,
      imageWidth: 48,
      imageHeight: 48,
    },
  ];

  const markerList = computed(() => {
    let tempMarkerList = [];
    let idx = 0;

    schedules.value.forEach((day) => {
      tempMarkerList.push([]);
      let id = 0;
      day.attractions.forEach((attraction) => {
        tempMarkerList[idx].push({
          key: id++,
          lat: attraction.latitude,
          lng: attraction.longitude,
          image: { ...images[idx % 3] },
          orderBottomMargin: "37px",
          order: id,
        });
      });
      idx++;
    });
    return tempMarkerList;
  });

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

  const top4Plans = ref([]);

  const uploadPlan = (planDetail, isUpdate) => {
    if (isUpdate) plan.value.id = planDetail.id;
    else plan.value.id = 0;
    plan.value.title = planDetail.title;
    plan.value.summary = planDetail.summary;
    schedules.value = [];
    day.value = 0;
    for (let schedule of planDetail.schedules) {
      day.value += 1;
      let attractionsObject = { attractions: [...schedule] };
      schedules.value.push(attractionsObject);
    }
  };

  const day = ref(1);
  const dynamicInherit = computed(() => {
    return `fixed top-[120px] [left:calc(120vw/5)] z-40 transition-transform ${
      isOpen.value ? "translate-x-[23rem]" : "translate-x-0"
    }`;
  });
  const isOpen = ref(false);

  const clear = () => {
    day.value = 1;
    plan.value.id = 0;
    plan.value.title = "";
    plan.value.imgUrl = "";
    plan.value.summary = "";
    plan.value.schedules = [];
    schedules.value = [
      {
        attractions: [],
      },
    ];
  };

  const incrementDay = () => {
    if (day.value <= 10) {
      day.value++;
      schedules.value.push({ attractions: [] });
    }
  };

  const decrementDay = () => {
    if (day.value > 1) {
      day.value--;
      schedules.value.pop();
    }
  };

  function popAttraction(day, id) {
    schedules.value[day - 1].attractions.splice(id, 1);
  }

  const toggleSidebar = () => {
    isOpen.value = !isOpen.value;
  };

  const dynamicImg = () => {
    return `${isOpen.value ? leftImg : rightImg}`;
  };

  return {
    categories,
    day,
    dynamicInherit,
    isOpen,
    isLoading,
    top4Plans,
    markerList,
    schedules,
    plan,
    plans,
    clear,
    decrementDay,
    dynamicImg,
    incrementDay,
    uploadPlan,
    popAttraction,
    toggleSidebar,
  };
});
