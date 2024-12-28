<script setup>
const { item } = defineProps({
    item: Object,
})
const createdAt = item.createdAt.split("T")[0]
</script>

<template>
    <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            <RouterLink :to="{'name':'notice-view', 'params':{id: item.boardId}}">
                {{ item.boardId }}
            </RouterLink>
        </th>
        <td class="px-6 py-4">
            <RouterLink :to="{'name':'notice-view', 'params':{id: item.boardId}}">
                {{ item.title }}
            </RouterLink>
        </td>
        <td class="px-6 py-4">
            {{ item.writer }}
        </td>
        <td class="px-6 py-4">
            {{ createdAt }}
        </td>
        <td class="px-6 py-4">
            0
        </td>
    </tr>
</template>

<style scoped>

</style>
