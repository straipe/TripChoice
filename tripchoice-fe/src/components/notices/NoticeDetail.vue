<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailNotice, deleteNotice } from "@/api/notice";
import { httpStatusCode } from "@/util/http-status"
import VButton from "@/components/common/VButton.vue"

const route = useRoute();
const router = useRouter();

// const id = ref(route.params.id);
const { id } = route.params;

const notice = ref({});

onMounted(() => {
  getNotice();
});

const getNotice = () => {
  detailNotice(
    id,
    ({ data }) => {
      notice.value = data;
      if(notice.value.createdAt) {
        const arr = notice.value.createdAt.split("T")
        arr[1] = arr[1].split(".")[0]
        notice.value.createdAt = arr.join(' ')
      }
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "notice-list" });
}

function moveModify() {
  console.log(id +"번 글 수정 페이지로 이동")
  router.push({ name: "notice-modify", params: { 'id': id } });
}

function onDeleteNotice() {
  console.log(id+"번 글 삭제 시도!!")
  deleteNotice(
    id,
    (response) => {
      if (response.status == httpStatusCode.NOCONTENT) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}

notice.value = { "id": 1, "title": "안녕하세요!", "author": "Jubin", "content": "안녕하세요 반갑습니다 잘 지내셨죠?","createdAt": "2024-11-25", "views": 5 }

</script>

<template>
  <div class="max-w-screen-sm mx-auto p-4">
    <h2 class="text-4xl font-extrabold dark:text-white mt-6 mb-6">글보기</h2>
    <div class="w-full h-[27rem] bg-white p-6 border border-gray-200 rounded-lg shadow">
      <h4 class="text-2xl font-extrabold dark:text-white mt-6 mb-6">{{ notice.title }}</h4>
      <div class="flex justify-between">
        <span>
          <span class="font-bold">작성자</span>: {{ notice.writer }}
        </span>
        <span class="text-gray-400">작성일: {{ notice.createdAt }} | 조회: {{ notice.hit }}</span>
      </div>
      <hr class="h-px my-5 bg-gray-200 border-0 dark:bg-gray-700">
      <div class="min-h-[10rem] max-h-[20rem]">{{ notice.content }}</div>
      <hr class="h-px my-5 bg-gray-200 border-0 dark:bg-gray-700">
      <div class="flex justify-end">
        <div class="flex space-x-4">
          <VButton @click="moveList" type="Move" title="목록"></VButton>
          <VButton @click="moveModify" type="Move" title="수정"></VButton>
          <VButton @click="onDeleteNotice" type="Delete" title="삭제"></VButton>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
