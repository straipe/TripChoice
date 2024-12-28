<script setup>

import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useModalStore } from '@/stores/modal';

const router = useRouter()
const modalStore = useModalStore()
const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore

const loginUser = ref({
  email: "",
  password: "",
})

const login = async () => {
  await userLogin(loginUser.value)
  let token = sessionStorage.getItem("accessToken")
  console.log(token)
  console.log("isLogin: " + isLogin.value)
  if (isLogin.value) {
    modalStore.closeModal()
    getUserInfo(token).then(
      () => {
        console.log(memberStore.userInfo)
        router.push(`/users/profile/${memberStore.userInfo.id}`)
      }
    )
  }
}
</script>

<template>
  <form class="space-y-4" @submit.prevent="login">
      <div>
          <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
          <input type="email" v-model="loginUser.email" name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="name@company.com" required />
      </div>
      <div>
          <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
          <input type="password" v-model="loginUser.password" name="password" id="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
      </div>
      <div class="flex justify-between">
          <div class="flex items-start">
              <div class="flex items-center h-5">
                  <input id="remember" type="checkbox" value="" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-600 dark:border-gray-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800" />
              </div>
              <label for="remember" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">아이디 저장하기</label>
          </div>
          <a href="#" class="text-sm text-blue-700 hover:underline">비밀번호 찾기</a>
      </div>
      <button type="submit" class="w-full text-white bg-limeGreen hover:bg-darkLimeGreen focus:ring-4 focus:outline-none font-bold rounded-lg text-sm px-5 py-2.5 text-center">
          로그인 하기
      </button>
      <div class="mb-3 text-start" v-if="isLoginError === true">
        <div class="alert alert-danger" role="alert">아이디 또는 비밀번호 확인해 주세요</div>
      </div>
      <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
          회원이 아니신가요? <RouterLink :to="{ name: 'user-join' }" class="nav-link text-blue-700 hover:underline" @click="modalStore.closeModal">회원 가입하기</RouterLink>
      </div>
  </form>
</template>

<style scoped>

</style>
