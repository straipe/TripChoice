<script setup>
import { ref, onMounted } from "vue";
import { listPlan } from "@/api/plan"
import { useMemberStore } from "@/stores/member";
import { usePlanStore } from "@/stores/plan";

import PlanCardList from '@/components/plans/PlanCardList.vue';
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const memberStore = useMemberStore()
const planStore = usePlanStore()

const currentPage = ref(1)
const totalPage = ref(5)
const { VITE_PLAN_LIST_SIZE } = import.meta.env
const isLoading = ref(true)

const param = ref({
  page: currentPage.value,
  size: VITE_PLAN_LIST_SIZE,
  author: memberStore.userInfo.name
})

onMounted(()=>{
  getPlanList();
})

const getPlanList = () => {
  console.log("서버에서 내 플랜 목록 얻어오자!!!", param.value)
  listPlan(
    param.value,
    ({ data }) => {
      console.log(data.content)
      planStore.plans = data.content
      currentPage.value = data.currentPage+1
      totalPage.value = data.totalPage
      isLoading.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!")
  currentPage.value = val
  param.value.page = val
  getPlanList()
}
</script>

<template>
  <PlanCardList v-if="!isLoading" :plans="planStore.plans" mode="MyPlan"></PlanCardList>
  <VPageNavigation class="mt-8" :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
</template>

<style scoped>

</style>
