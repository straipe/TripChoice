<script setup>
import { ref, watch } from 'vue'
import { registPlan, modifyPlan } from "@/api/plan"
import { useRouter } from "vue-router";
import { usePlanStore } from "@/stores/plan";
import { useMemberStore } from "@/stores/member";
import { useModalStore } from "@/stores/modal";

const planStore = usePlanStore()
const memberStore = useMemberStore()
const modalStore = useModalStore()

const router = useRouter();

const titleErrMsg = ref("");
const contentErrMsg = ref("");

watch(
  () => planStore.plan.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목의 길이는 1자 이상 30자 이하 입니다.";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);

watch(
  () => planStore.plan.summary,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      contentErrMsg.value = "한 줄 설명 길이는 1자 이상 30자 이하 입니다.";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    planStore.plan.id === 0 ? writePlan() : updatePlan();
  }
}

function writePlan() {
  console.log("플랜 등록하자!!");

  //스케줄 처리
  planStore.schedules.forEach(daySchedule => {
    let value = daySchedule.attractions
    .filter(attraction => attraction.no)
    .map(attraction => attraction.no)
    let data = { attractionIds : value }
    planStore.plan.schedules.push(data)
  });

  //이미지 처리
  let firstImg = ""
  let find = false
  for(let schedule of planStore.schedules){
    for(let attraction of schedule.attractions){
        if(attraction.imgUrl !== '') {
          firstImg = attraction.imgUrl
          find = true
          break
      }
      if(find) break
    }
    if(find) break
  }
  planStore.plan.imgUrl = find ? firstImg : ""

  console.log(planStore.plan)
  registPlan(
    planStore.plan,
    (response) => {
      let msg = "플랜 등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "플랜 등록이 완료되었습니다.";
      alert(msg);
      planStore.clear()
      modalStore.closeModal()
      router.push(`/users/profile/${memberStore.userId}/plans`);
    },
    (error) => {
      console.error(error)
      modalStore.closeModal()
    }
  );
}

function updatePlan() {
  console.log(planStore.plan.id + "번 플랜 수정하자!!", planStore.plan);

  //스케줄 처리
  planStore.schedules.forEach(daySchedule => {
    let value = daySchedule.attractions
    .filter(attraction => attraction.no)
    .map(attraction => attraction.no)
    let data = { attractionIds : value }
    planStore.plan.schedules.push(data)
  });

  //이미지 처리
  let firstImg = ""
  let find = false
  for(let schedule of planStore.schedules){
    for(let attraction of schedule.attractions){
        if(attraction.imgUrl !== '') {
          firstImg = attraction.imgUrl
          find = true
          break
      }
      if(find) break
    }
    if(find) break
  }
  planStore.plan.imgUrl = find ? firstImg : ""

  modifyPlan(
    planStore.plan,
    (response) => {
      let msg = "플랜 수정 처리시 문제 발생했습니다.";
      if (response.status == 204) msg = "플랜 정보 수정이 완료되었습니다.";
      alert(msg);
      planStore.clear()
      modalStore.closeModal()
      router.push(`/users/profile/${memberStore.userId}/plans`);
    },
    (error) => {
      console.log(error)
      modalStore.closeModal()
    }
  );
}


</script>

<template>
  <form class="space-y-4">
      <div>
          <label for="title" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">제목</label>
          <input type="text" name="title" v-model="planStore.plan.title" id="title" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500" placeholder="경복궁" required />
      </div>
      <div>
          <label for="summary" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">한 줄 설명</label>
          <input type="text" name="summary" v-model="planStore.plan.summary" id="summary" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500" placeholder="우리나라 역사와 전통이 깃들어 있는 공간입니다." required />
      </div>
      <button type="button" @click="onSubmit" class="w-full text-white bg-limeGreen hover:bg-darkLimeGreen focus:ring-4 focus:outline-none font-bold rounded-lg text-sm px-5 py-2.5 text-center">
          등록하기
      </button>
  </form>
</template>

<style scoped>

</style>
