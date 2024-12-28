<script setup>
import PlanCardList from "@/components/plans/PlanCardList.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";
import VSelect from "@/components/common/VSelect.vue";
import { ref, onMounted } from "vue"
import { listPlan } from "@/api/plan"
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();

const orders = [
  { text: '정렬기준', value:'' },
  { text: '좋아요순', value:'like' },
  { text: '최신순', value:'last' },
]
const currentPage = ref(1)
const totalPage = ref(5)

const { VITE_PLAN_LIST_SIZE } = import.meta.env

const param = ref({
  page: currentPage.value,
  size: VITE_PLAN_LIST_SIZE,
})

const changeOrder = (val) => {
  console.log("PlanList에서 선택한 정렬기준 : " + val)
  param.value.order = val
}

const getPlanList = () => {
  console.log("서버에서 플랜 목록 얻어오자!!!", param.value)
  listPlan(
    param.value,
    ({ data }) => {
      console.log(data.content)
      planStore.plans = data.content
      currentPage.value = data.currentPage+1
      totalPage.value = data.totalPage
      planStore.isLoading = false
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

onMounted(() => {
  getPlanList();
})

</script>

<template>
  <div class="bg-gray-100 min-h-[58rem]">
    <div class="max-w-screen-lg mx-auto p-4">
      <div class="flex justify-between">
        <h2 class="text-4xl font-extrabold dark:text-white mt-6 mb-6">공유 게시판</h2>
        <div class="mt-8">
          <VSelect type="Box" @onKeySelect="changeOrder" :select-option="orders"></VSelect>
        </div>
      </div>
      <div class="w-full h-[50rem]">
        <PlanCardList :plans="planStore.plans" mode="OtherPlan"></PlanCardList>
        <VPageNavigation class="mt-8" :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
