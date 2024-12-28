<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registNotice, getModifyNotice, modifyNotice } from "@/api/notice";
import { httpStatusCode } from "@/util/http-status"
import VButton from "@/components/common/VButton.vue"

const props = defineProps({
  mode: String,
})
const router = useRouter();
const route = useRoute();

const notice = ref({
  title: "",
  content: "",
});

if (props.mode === "Modify") {
  let { id } = route.params;
  console.log(id+"번 글 수정 API 요청")
  getModifyNotice(
    id,
    ({ data }) => {
      notice.value = data;
    },
    (error) => {
      console.error(error);
      console.log(id+"번 글 수정하려다 실패")
    }
  );
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => notice.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목의 길이는 1자 이상 30자 이하 입니다.";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => notice.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "글의 길이는 1자 이상 500자 이하 입니다.";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();
  console.log('글 등록 시작!!')
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.mode === "Regist" ? writeNotice() : updateNotice();
  }
}

function writeNotice() {
  console.log("글등록하자!!", notice.value);
  registNotice(
    notice.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == httpStatusCode.CREATE) msg = "글등록이 완료되었습니다.";
      alert(msg);
      //moveList();
      router.push(`/notices/list`);
    },
    (error) => console.error(error)
  );
}

function updateNotice() {
  console.log(notice.value.boardId + "번글 수정하자!!", notice.value);
  modifyNotice(
    notice.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == httpStatusCode.NOCONTENT) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      //moveList();
      router.push(`/notices/list`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "notice-list" });
}
</script>

<template>
  <div class="w-full h-[27rem] bg-white p-6 border border-gray-200 rounded-lg shadow">
      <form class="max-w-lg mx-auto">
          <div class="mt-5 mb-5">
              <label for="title" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">제목</label>
              <div class="flex">
                  <input type="text" id="title" v-model="notice.title" placeholder="제목을 입력해주세요." class="w-full h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
              </div>
          </div>
          <div class="mb-10">
              <label for="content" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">내용</label>
              <textarea id="content" v-model="notice.content" placeholder="내용을 입력해주세요." class="h-[10rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700" required />
          </div>
      </form>
      <div class="flex justify-end">
        <div class="flex space-x-4">
          <VButton @click.prevent="moveList" type="Move" title="목록" ></VButton>
          <VButton type="Submit" @submit="onSubmit" title="수정" v-if="mode==='Modify'"></VButton>
          <VButton type="Submit" @submit="onSubmit" title="작성" v-else-if="mode==='Regist'"></VButton>
        </div>
      </div>
    </div>
</template>

<style scoped></style>
