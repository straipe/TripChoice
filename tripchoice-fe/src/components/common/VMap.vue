<script setup>
import { KakaoMap, KakaoMapMarker, KakaoMapMarkerPolyline } from 'vue3-kakao-maps'
import { useAttractionStore } from '@/stores/attraction'
import { usePlanStore } from '@/stores/plan';
import { ref, watch } from 'vue'
import marker1 from "@/assets/icon/marker1.png";
import marker2 from "@/assets/icon/marker2.png";
import marker3 from "@/assets/icon/marker3.png";
import marker4 from "@/assets/icon/marker4.png";
import marker5 from "@/assets/icon/marker5.png";
import marker6 from "@/assets/icon/marker6.png";

import noImage from "@/assets/images/no_image.png"

const attractionStore = useAttractionStore()
const planStore = usePlanStore()

const coordinate = ref({
  lat : attractionStore.attractions[0].latitude,
  lng : attractionStore.attractions[0].longitude,
})

watch(
  () => attractionStore.attractions[0],
  (newAttraction) => {
    coordinate.value.lat = newAttraction.latitude
    coordinate.value.lng = newAttraction.longitude
  },
  { immediate: true } // 초기값도 설정하도록 immediate 설정
)

const visibleList = ref([])
const map = ref()

watch(
  () => attractionStore.attractions,
  () => {
    onLoadKakaoMap(map.value)
    setBounds()
  }
)

// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
let bounds;

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  bounds = new kakao.maps.LatLngBounds();
  let point;
  attractionStore.attractions.forEach((attraction) => {
    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
    point = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);

    // LatLngBounds 객체에 좌표를 추가합니다
    bounds.extend(point);
  });
};

const setBounds = () => {
  // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
  // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
  if (map.value !== undefined) {
    map.value.setBounds(bounds);
  }
};

const getmarker = (contentType) => {
  if(contentType==='문화시설') return marker2
  else if(contentType==='축제공연행사') return marker3
  else if(contentType==='여행코스') return marker4
  else if(contentType==='레포츠') return marker5
  else if(contentType==='숙박') return marker6
  else return marker1
}

const mouseOverKakaoMapMarker = (index, lat, lng) => {
  visibleList.value[index] = true;
  panTo(lat, lng)
};

const mouseOutKakaoMapMarker = (index) => {
  visibleList.value[index] = false;
};

const panTo = (lat, lng) => {
  if (map.value) {
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.value.panTo(new kakao.maps.LatLng(lat, lng));
  }
};

const getColor = (index) => {
  index %= 3
  if(index===0) return '#F05252'
  else if(index===1) return '#1C64F2'
  else if(index===2) return '#31C48D'

}

</script>

<template>
  <KakaoMap @onLoadKakaoMap="onLoadKakaoMap" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true" style="width: 100%; height: 100%">
    <KakaoMapMarker v-for="(attraction, index) in attractionStore.attractions" :key="index"
      :lat="attraction.latitude" :lng="attraction.longitude" :image="{
        imageSrc: getmarker(attraction.contentType),
        imageWidth: 48,
        imageHeight: 48,
      }"
      @mouseOverKakaoMapMarker="() => mouseOverKakaoMapMarker(index, attraction.latitude, attraction.longitude)"
      @mouseOutKakaoMapMarker="() => mouseOutKakaoMapMarker(index)">
      <template v-slot:infoWindow v-if="visibleList[index]">
        <div class="flex flex-row items-center bg-white border border-gray-200 shadow hover:bg-gray-100">
          <img class="object-cover w-[8rem] rounded-t-lg h-auto rounded-s-lg" :src="attraction.imgUrl !== '' ? attraction.imgUrl : noImage" alt="">
          <div class="flex flex-col justify-between p-2 pr-3 leading-normal">
              <h5 class="mb-2 text-md font-bold tracking-tight text-gray-900 dark:text-white">{{ attraction.title }}</h5>
              <p class="font-normal text-gray-700 dark:text-gray-400">{{ attraction.address }}</p>
          </div>
        </div>
      </template>
    </KakaoMapMarker>
    <KakaoMapMarkerPolyline v-for="(dayMarkerList, index) in planStore.markerList" :markerList="dayMarkerList"
    :showMarkerOrder="true"
    :strokeColor="getColor(index)"
    :strokeOpacity="1"
    />
  </KakaoMap>
</template>

<style scoped>

</style>

