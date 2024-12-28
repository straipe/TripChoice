<script setup>

import VButton from "@/components/common/VButton.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";
import PlanDayList from "@/components/plans/PlanDayList.vue";
import { computed, ref, onMounted } from 'vue'
import { useMemberStore } from "@/stores/member"
import { useModalStore } from "@/stores/modal"
import { usePlanStore } from "@/stores/plan"
import { useRouter } from 'vue-router'
import { detailPlan, deletePlan } from "@/api/plan"
import noImage from "@/assets/images/no_image.png"
import { KakaoMap, KakaoMapMarkerPolyline } from "vue3-kakao-maps";
import { httpStatusCode } from "@/util/http-status"
import planMarker1 from "@/assets/icon/planMarker1.png"
import planMarker2 from "@/assets/icon/planMarker2.png"
import planMarker3 from "@/assets/icon/planMarker3.png"

const router = useRouter()

const { content } = defineProps({
  mode: String,
  content: Object,
})

const currentPage = ref(1)
const totalPage = ref(1)
const isLoading = ref(true)

const modalStore = useModalStore()
const memberStore = useMemberStore()
const planStore = usePlanStore()

const { VITE_DAY_PLAN_LIST_SIZE } = import.meta.env
const param = ref({
  page: currentPage.value,
  size: VITE_DAY_PLAN_LIST_SIZE,
})

const planDetail = ref({})
const markerList = ref([])

const images = [
  {
    imageSrc: planMarker1,
    imageWidth: 48,
    imageHeight: 48
  },
  {
    imageSrc: planMarker2,
    imageWidth: 48,
    imageHeight: 48
  },
  {
    imageSrc: planMarker3,
    imageWidth: 48,
    imageHeight: 48
  },
]

// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
const map = ref()
let bounds;

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  bounds = new kakao.maps.LatLngBounds();
  let point;
  for(let schedule of planDetail.value.schedules){
    for(let attraction of schedule){
      point = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(point);
    }
  }
  setBounds()

};

const setBounds = () => {
  // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
  // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
  if (map.value !== undefined) {
    map.value.setBounds(bounds);
  }
};

const getColor = (index) => {
  index %= 3
  if(index===0) return '#F05252'
  else if(index===1) return '#1C64F2'
  else if(index===2) return '#31C48D'

}

onMounted(()=>{
  detailPlan(content.id,
    ({data}) => {
      planDetail.value = data
      console.log(data)
      isLoading.value = false

      let idx = 0
      for(let schedule of planDetail.value.schedules){
        markerList.value.push([])
        let id = 0
        for(let attraction of schedule){
          markerList.value[idx].push({ key: id++, lat: attraction.latitude, lng: attraction.longitude,
            image:{ ...images[idx%3] }, orderBottomMargin: '37px', order: id})
        }
        idx++
      }
    },
    (error) => {
      console.error(error);
    }
  )
})

const getFirstLatLng = computed(()=> {
  if(!isLoading.value)
    return {
      lat: planDetail.value.schedules[0][0].latitude,
      lng: planDetail.value.schedules[0][0].longitude,
    }
  else {
    return {
      lat: 37.501286,
      lng: 127.0396029,
    }
  }
})

const removePlan = () =>{
  deletePlan(content.id,
    (response) => {
      if (response.status == httpStatusCode.NOCONTENT) moveList();
    },
    (error) => {
      console.error(error);
    }
  )
}

function moveUpdate(isUpdate){
  planStore.uploadPlan(planDetail.value, isUpdate)
  planStore.isOpen = true
  modalStore.closeModal()
  router.push({ name :'attractions'})
}

function moveList() {
  router.push({ name: "user-profile", params: { id: memberStore.userInfo.id } });
}

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!")
  currentPage.value = val
  param.value.page = val
}
</script>

<template>
  <div class="grid grid-cols-2 gap-5 divide-x-5">
    <div class="col-span-1">
      <!-- <p class="font-bold text-sm ml-5">{{ planDetail.category }}</p> -->
      <div class="flex flex-col items-center mt-3">
        <img v-if="!isLoading" :src="planDetail.imgUrl ? planDetail.imgUrl : noImage" class="object-cover w-[28rem] h-[15rem] rounded-xl shadow"/>
        <p class="mt-6 ml-5 self-start text-sm">
          <span class="font-bold">작성자 : </span>
          <span>{{ planDetail.userName }}</span>
        </p>
        <p class="mt-6 ml-5 self-start text-sm">
          <span class="font-bold">소개 : </span>
          <span>{{ planDetail.summary }}</span>
        </p>
        <p class="mt-6 ml-5 self-start text-sm">
          <span class="font-bold">경로</span>
        </p>
        <div class="mt-3 text-sm">
          <KakaoMap v-if="!isLoading" @onLoadKakaoMap="onLoadKakaoMap" style="width:28.75rem; height: 20rem" :lat="getFirstLatLng.lat" :lng="getFirstLatLng.lng" >
            <KakaoMapMarkerPolyline v-for="(dayMarkerList, index) in markerList" :markerList="dayMarkerList"
              :showMarkerOrder="true"
              :strokeColor="getColor(index)"
              :strokeOpacity="1"
              />
          </KakaoMap>
        </div>
        <VButton v-if="mode==='OtherPlan'" @click="moveUpdate(false)" type="Modify" title="플랜 업로드하기" class="mt-8 w-[90%]"></VButton>
        <div v-else-if="mode==='MyPlan'" class="grid grid-cols-2 w-[90%]">
          <VButton @click="moveUpdate(true)" type="Modify" title="수정" class="col-span-1 mt-3 mx-2"></VButton>
          <VButton @click="removePlan" type="Delete" title="삭제" class="col-span-1 mt-3 mx-2"></VButton>
        </div>
      </div>
    </div>
    <div class="col-span-1">
        <p class="font-bold text-sm ml-5">일정</p>
        <ol class="relative border-s border-gray-200 dark:border-gray-700">
          <PlanDayList v-for="(scheduleByDay, index) in planDetail.schedules" :key="index" :num="index+1" :scheduleByDay="scheduleByDay"></PlanDayList>
        </ol>
        <VPageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
    </div>
  </div>
</template>

<style scoped>

</style>
