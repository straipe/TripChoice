<script setup>
import { ref, watch } from 'vue'
import { useAttractionStore } from "@/stores/attraction";
import { useMemberStore } from '@/stores/member';
import { useModalStore } from '@/stores/modal';
import VButton from "@/components/common/VButton.vue";
import CategoryFilter from "@/components/attractions/CategoryFilter.vue";
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { registHotPlace, modifyHotPlace, listHotPlace } from '@/api/hotplace';
import { httpStatusCode } from "@/util/http-status"
import { useHotPlaceStore } from '@/stores/hotplace';
const { mode } = defineProps({
    mode: {
        type: String,
        default: "HotPlaceRegist",
    },
})
const attractinoStore = useAttractionStore()
const memberStore = useMemberStore()
const modalStore = useModalStore()
const hotPlaceStore = useHotPlaceStore()
const categories = attractinoStore.categories

const hotPlace = ref({
  id: 0,
  address: "",
  image: "",
  latitude: 37.501286,
  longitude: 127.0396029,
  summary: "",
  title: "",
  userId: memberStore.userInfo.id,
  contentTypeId: "",
});

const onFileChange = (event) => {
  const file = event.target.files[0]
  if(file){
    const reader = new FileReader()
    reader.onload = (e) =>{
      hotPlace.value.image = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const changeCategory = (val) => {
  hotPlace.value.contentTypeId = val
}

const titleErrMsg = ref("");
const summaryErrMsg = ref("");

watch(
  () => hotPlace.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목의 길이는 1자 이상 30자 이하 입니다.";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => hotPlace.value.summary,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      summaryErrMsg.value = "글의 길이는 1자 이상 500자 이하 입니다.";
    } else summaryErrMsg.value = "";
  },
  { immediate: true }
);

const map = ref()
const onLoadKakaoMap = (mapRef) =>{
  map.value = mapRef;

  kakao.maps.event.addListener(map.value, 'click', function(mouseEvent){
    const latlng = mouseEvent.latLng;
    hotPlace.value.latitude = latlng.getLat()
    hotPlace.value.longitude = latlng.getLng()
    console.log(`현재 위도: ${hotPlace.value.latitude} 현재 경도: ${hotPlace.value.longitude}`)
  })
}

function onSubmit() {
  console.log('핫플 등록 시작!!')
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (summaryErrMsg.value) {
    alert(summaryErrMsg.value);
  } else {
    mode === "HotPlaceRegist" ? writeHotPlace() : updateHotPlace();
  }
}

function writeHotPlace() {
  console.log("핫플 등록하자!!", hotPlace.value);
  registHotPlace(
    hotPlace.value,
    (response) => {
      let msg = "핫플 등록 처리시 문제 발생했습니다.";
      if (response.status == httpStatusCode.CREATE) msg = "핫플 등록이 완료되었습니다.";
      alert(msg);
      getHotPlaceList()
      modalStore.closeModal()
    },
    (error) => console.error(error)
  );
}

const currentPage = ref(1)
const totalPage = ref(5)
const { VITE_HOTPLACE_LIST_SIZE } = import.meta.env

const param = ref({
  page: currentPage.value,
  size: VITE_HOTPLACE_LIST_SIZE,
})

const getHotPlaceList = () => {
  console.log("서버에서 핫플 목록 얻어오자!!!", param.value)
  listHotPlace(
    param.value,
    ({ data }) => {
      console.log(data)
      hotPlaceStore.hotPlaces = data.content
      console.log("hotPlaces.value = " + hotPlaceStore.hotPlaces)
      currentPage.value = data.currentPage
      totalPage.value = data.totalPage
    },
    (error) => {
      console.log(error)
    }
  )
}

function updateHotPlace() {
  console.log(hotPlace.value.id + "번 핫플 수정하자!!", hotPlace.value);
  modifyHotPlace(
    hotPlace.value,
    (response) => {
      let msg = "핫플 수정 처리시 문제 발생했습니다.";
      if (response.status == httpStatusCode.NOCONTENT) msg = "핫플 정보 수정이 완료되었습니다.";
      alert(msg);
    },
    (error) => console.log(error)
  );
}

</script>

<template>
  <form class="space-y-4" @submit.prevent="onSubmit">
      <div class="flex flex-col items-center justify-center w-full mb-8">
          <label for="img" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white self-start">사진</label>
          <label v-if="hotPlace.image ==='' " for="img" class="flex flex-col items-center justify-center w-full h-[20rem] border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
              <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                  </svg>
                  <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                  <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
              </div>
              <input id="img" type="file" class="hidden" @change="onFileChange"/>
          </label>
          <label v-else class="flex flex-col items-center justify-center w-full h-[20rem] rounded-lg cursor-pointer">
            <img :src="hotPlace.image" class="h-[20rem] w-full rounded-lg" />
            <input id="img" type="file" class="hidden" @change="onFileChange"/>
          </label>

      </div>
      <div>
          <label for="title" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">제목</label>
          <input type="text" name="title" id="title" v-model="hotPlace.title" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500" placeholder="경복궁" required />
      </div>
      <div>
          <label for="category" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">카테고리</label>
          <CategoryFilter @onSelect1="changeCategory" :categories="categories" type="Select"></CategoryFilter>
      </div>
      <div>
          <label for="address" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">주소</label>
          <input type="text" name="address" id="address" v-model="hotPlace.address" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500" placeholder="서울특별시 종로구 사직로 161" required />
      </div>
      <div>
          <label for="summary" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">한 줄 설명</label>
          <input type="text" name="summary" id="summary" v-model="hotPlace.summary" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500" placeholder="우리나라 역사와 전통이 깃들어 있는 공간입니다." required />
      </div>
      <div>
        <label for="location" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">위치</label>
        <div class="flex items-center">
          <KakaoMap style="width: 100% ;height: 30rem" :lat="hotPlace.latitude" :lng="hotPlace.longitude" @on-load-kakao-map="onLoadKakaoMap">
            <KakaoMapMarker :lat="hotPlace.latitude" :lng="hotPlace.longitude" />
          </KakaoMap>
        </div>
      </div>
      <VButton title="등록하기" type="Submit2"></VButton>
  </form>
</template>

<style scoped>

</style>
