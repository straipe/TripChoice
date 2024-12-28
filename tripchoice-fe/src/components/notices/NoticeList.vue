<script setup>
import { ref, onMounted, provide, watch } from 'vue'
import { useRouter } from "vue-router";
import { listNotice } from "@/api/notice.js"
import { useNoticeStore } from "@/stores/notice"

import VButton from "@/components/common/VButton.vue";
import VSearch from "@/components/common/VSearch.vue";
import VSelect from "@/components/common/VSelect.vue";
import NoticeTable from "@/components/notices/item/NoticeTable.vue";
import VPageNavigation from "../common/VPageNavigation.vue";

const router = useRouter()
const noticeStore = useNoticeStore()

const selectOption = [
  { text: '검색조건',   value: "" },
  { text: '글번호', value: "ID"},
  { text: '제목',   value: "TITLE" },
  { text: '작성자', value: "WRITER" },
]

const currentPage = ref(1)
const totalPage = ref(5)
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env
const param = ref({
  page: currentPage.value,
  size: VITE_ARTICLE_LIST_SIZE,
  condition: "",
  keyword: "",
})

onMounted(() => {
  getNoticeList()
})

const changeKey = (val) => {
  console.log("NoticeList에서 선택한 조건 : " + val)
  param.value.condition = val
}

const getNoticeList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value)
  listNotice(
    param.value,
    ({ data }) => {
      console.log("성공했다!!!")
      console.log(data)
      noticeStore.notices = data.content
      console.log("notices.value = " + noticeStore.notices)
      currentPage.value = data.currentPage + 1
      totalPage.value = data.totalPage
    },
    (error) => {
      console.log(error)
    }
  )
}


const searchNotice = (keyword) =>{
  param.value.page = 1
  param.value.keyword = keyword
  console.log("search 시작!!")
  getNoticeList()
}

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!")
  currentPage.value = val
  param.value.page = val
  getNoticeList()
}

function moveRegist() {
  router.push({ name: "notice-write" });
}
</script>

<template>
  <div class="max-w-screen-lg mx-auto p-4">
    <h2 class="text-4xl font-extrabold dark:text-white mt-6 mb-6">공지사항</h2>
    <div class="w-full h-[50rem] bg-white p-6 border border-gray-200 rounded-lg shadow">
      <div class="flex items-center justify-between">
        <VButton @click.prevent="moveRegist" type="Submit" title="글쓰기"></VButton>
        <div class="flex items-center">
          <VSelect type="Box" :selectOption="selectOption" @onKeySelect="changeKey" attr="mr-3 p-3"></VSelect>
          <VSearch @clickButton="searchNotice"></VSearch>
        </div>
      </div>
      <div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-3">
          <NoticeTable></NoticeTable>
      </div>
      <VPageNavigation class="mt-8" :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
    </div>
  </div>
</template>

<style scoped>

</style>
