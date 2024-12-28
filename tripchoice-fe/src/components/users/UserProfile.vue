<script setup>
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore()

</script>

<template>
    <div v-if="memberStore.userInfo">
        <h2 class="text-4xl font-extrabold dark:text-white mt-6 mb-6">마이페이지</h2>
        <div class="w-[48rem] mb-4 h-auto bg-white p-6 border border-gray-200 rounded-lg shadow grid grid-cols-5 divide-x-[3px]">
          <RouterLink :to="{'name':'user-profile-info', 'params':{'id': memberStore.userInfo.id}}" class="font-bold text-lg cols-span-1 pl-4">
            내 정보
          </RouterLink>
          <RouterLink :to="{'name':'user-profile-plans', 'params':{'id': memberStore.userInfo.id}}" class="font-bold text-lg cols-span-1 pl-4">
            내 플랜
          </RouterLink>
        </div>
        <RouterView></RouterView>
    </div>
</template>

<style scoped>

</style>
