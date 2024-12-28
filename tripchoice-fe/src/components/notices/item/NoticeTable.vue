<script setup>
import NoticeTableHeader from "@/components/notices/item/NoticeTableHeader.vue"
import NoticeTableBody from "@/components/notices/item/NoticeTableBody.vue"
</script>

<template>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <NoticeTableHeader></NoticeTableHeader>
        <NoticeTableBody></NoticeTableBody>
    </table>
</template>

<style scoped>

</style>