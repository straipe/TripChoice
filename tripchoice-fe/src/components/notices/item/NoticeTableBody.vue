<script setup>
import NoticeTableBodyItem from "@/components/notices/item/NoticeTableBodyItem.vue";
import { useNoticeStore } from "@/stores/notice"

const noticeStore = useNoticeStore()

</script>

<template>
    <tbody>
        <NoticeTableBodyItem v-for="item in noticeStore.notices" :key="item.boardId" :item="item"></NoticeTableBodyItem>
    </tbody>
</template>

<style scoped>

</style>
