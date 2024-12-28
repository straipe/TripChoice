<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router'
import { useMemberStore } from "@/stores/member"
import { usePlanStore } from '@/stores/plan'
import { logout } from "@/api/user"
import { computed, ref } from 'vue'
import { httpStatusCode } from '@/util/http-status'
import VButton from "@/components/common/VButton.vue"
import logo from "@/assets/images/logo.png"
const route = useRoute()
const router = useRouter()
const planStore = usePlanStore()

const dynamicClass = computed(() => {
  return route.path !== '/attractions' ? 'max-w-screen-xl mx-auto' : '';
});

const memberStore = useMemberStore()

const userLogout = () => {
    logout(
        { 'refreshToken': sessionStorage.getItem("refreshToken") },
        (response) => {
            console.log("로그아웃 중...")
            if (response.status === httpStatusCode.NOCONTENT) {
              alert("로그아웃 성공!!")
              router.push(`/`)
              memberStore.isLogin = false
              memberStore.userInfoClear()
              memberStore.isValidToken = false;
              sessionStorage.removeItem("accessToken");
              sessionStorage.removeItem("refreshToken");
            }
        },
        (error) => {
            console.log("로그아웃 실패....")
            console.error(error);
        }
    )
}

</script>

<template>
    <nav class="bg-white border-gray-200 border-b dark:bg-gray-900 fixed top-0 left-0 w-full z-50">
    <div :class="dynamicClass" class="flex flex-wrap items-center justify-between p-4">
        <RouterLink :to="{'name':'main'}" class="flex items-center space-x-3 rtl:space-x-reverse">
            <img :src="logo" alt="TripChoice Logo" class="w-40"/>
        </RouterLink>

        <div class="flex md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
            <VButton v-if="!memberStore.userInfo.email" type="Login" title="로그인"></VButton>
            <div v-else class="flex space-x-4">
                <RouterLink :to="`/users/profile/${memberStore.userInfo.id}`" class="font-bold block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen">
                <div v-if="!memberStore.userInfo.image" class="relative w-9 h-9 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
                    <svg class="absolute w-11 h-11 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
                </div>
                <img v-else :src="memberStore.userInfo.image" class="w-10 h-10 rounded-full" />
                </RouterLink>
                <VButton type="Delete" @click="userLogout" title="로그아웃"></VButton>
            </div>

            <button data-collapse-toggle="navbar-cta" type="button" class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-cta" aria-expanded="false">
                <span class="sr-only">Open main menu</span>
                <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 1h15M1 7h15M1 13h15"/>
                </svg>
            </button>
        </div>
        <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-cta">
            <ul class="flex font-bold text-lg p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-16 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
            <li>
                <RouterLink :to="{'name':'main'}" class="block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen" aria-current="page">
                    Home
                </RouterLink>
            </li>
            <li>
                <RouterLink :to="{'name':'attractions'}" @click="planStore.isOpen = false" class="block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen">
                    관광지
                </RouterLink>
            </li>
            <li>
                <RouterLink :to="{'name':'hotplaces'}" class="block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen">
                    HotPlace
                </RouterLink>
            </li>
            <li>
                <RouterLink :to="{'name':'notices'}" class="block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen">
                    공지사항
                </RouterLink>
            </li>
            <li>
                <RouterLink :to="{'name':'plans'}" class="block py-2 px-3 md:p-0 text-gray-900 rounded hover:text-darkLimeGreen">
                    공유 게시판
                </RouterLink>
            </li>
            </ul>
        </div>
    </div>
    </nav>

</template>

<style scoped>
</style>
