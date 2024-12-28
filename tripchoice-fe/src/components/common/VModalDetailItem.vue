<script setup>
import noImage from "@/assets/images/no_image.png"
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
defineProps({
  mode: String,
  content: Object,
})
</script>

<template>
  <div class="flex flex-col items-center mt-3">
    <img :src="content.imgUrl ? content.imgUrl : noImage" class="object-cover w-[28rem] h-[15rem] rounded-xl shadow"/>
    <p v-if="mode!=='Profile' && content.address" class="mt-3 ml-5 self-start text-sm">
      <span class="font-bold">주소 : </span>
      <span>{{ content.address }}</span>
    </p>
    <p v-if="mode==='HotPlace'" class="mt-3 ml-5 self-start text-sm">
      <span class="font-bold">한줄평 : </span>
      <span>{{ content.summary }}</span>
    </p>
    <p v-if="mode==='HotPlace'" class="mt-3 ml-5 self-start text-sm">
      <span class="font-bold" v-if="mode==='HotPlace'">위치</span>
    </p>
    <div v-if="mode==='HotPlace'" class="mt-3 text-sm">
      <KakaoMap style="width:28.75rem; height: 20rem" :lat="content.latitude" :lng="content.longitude">
        <KakaoMapMarker :lat="content.latitude" :lng="content.longitude"/>
      </KakaoMap>
    </div>

  </div>
</template>

<style scoped>

</style>
