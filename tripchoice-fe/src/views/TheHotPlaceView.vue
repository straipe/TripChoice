<script setup>
import { ref, watch, onMounted } from 'vue'
import { useAttractionStore } from "@/stores/attraction";
import { useHotPlaceStore } from '@/stores/hotplace';
import { listHotPlace } from "@/api/hotplace"
import { listGuGuns } from "@/api/attraction"
import CategoryFilter from "@/components/attractions/CategoryFilter.vue";
import PositionFilter from '@/components/attractions/PositionFilter.vue';
import HotPlaceList from '@/components/attractions/HotPlaceList.vue';
import VSelect from "@/components/common/VSelect.vue";
import VSearch from "@/components/common/VSearch.vue";
import VToggle from "@/components/common/VToggle.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const attractionStore = useAttractionStore()
const hotPlaceStore = useHotPlaceStore()

const currentPage = ref(1)
const totalPage = ref(1)
const { VITE_HOTPLACE_LIST_SIZE } = import.meta.env

const param = ref({
  sido: '',
  content_type_id: '',
  gugun: '',
  radius: 10,
  word: '',
  key: "",
  page: currentPage.value,
  size: VITE_HOTPLACE_LIST_SIZE,
  order: '',
})

onMounted(()=>{
  hotPlaceStore.isLoading = true
  getHotPlaceList()
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
      hotPlaceStore.isLoading = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const order = [
  { text: '정렬기준', value:'' },
  { text: '좋아요순', value:'like' },
  { text: '최신순', value:'last' },
]

const searchByCategory = () =>{
  console.log("searchByCategory 시작!!")
  getHotPlaceList()
}

const searchByWord = () =>{
  console.log("searchByWord 시작!!")
  param.value.key="word"
  getHotPlaceList()
}

const searchByAddress = () =>{
  console.log("searchByAddress 시작!!")
  param.value.word=""
  param.value.key="address"
  getHotPlaceList()
}

const searchByLocation = () =>{
  console.log("searchByLocation 시작!!")
  param.value.word=""
  param.value.key="location"
  getHotPlaceList()
}

let debounceTimer;

const onParamChange = (key) => {
  console.log("현재 Param: ", param.value);

  if (debounceTimer) {
    clearTimeout(debounceTimer);
  }

  debounceTimer = setTimeout(() => {
    console.log("Debounce 완료. search 실행!!")
    console.log("현재 타입: "+ key)
    currentPage.value = 1
    param.value.page = 1
    if(key==='address') searchByAddress()
    else if(key==='location') searchByLocation()
    else if(key==='word') searchByWord()
    else searchByCategory()
  }, 1000);
};

watch( () => param.value.sido, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('address')
});

watch( () => param.value.gugun, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('address')
});

watch( () => param.value.radius, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('location')
});

watch( () => param.value.word, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('word')
});

watch( () => param.value.content_type_id, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('category')
});

const isChecked = ref(false)

watch(isChecked, (newVal, oldVal) => {
  if(oldVal === false) {
    console.log("주소에서 위치로 방식 변경했습니다!")
    onParamChange('location')
  }
})

const changeAddress = (...address) => {
  if (address[1] === 'sido') {
    if(address[0] !=='0' && address[0] !=='') {
      param.value.sido = attractionStore.getTextByValue(attractionStore.sidos, address[0])
      listGuGuns(
        address[0],
        ({ data }) => {
          attractionStore.guguns = data
          attractionStore.guguns.unshift({ text: '구/군', value: '' })
        },
        (error) => {
          console.log(error)
        }
      )
    }
    else {
      param.value.sido = ''
      attractionStore.guguns.length = 0
      attractionStore.guguns.unshift({ text: '전체', value: '0' })
      attractionStore.guguns.unshift({ text: '구/군', value: '' })
    }
    console.log("현재 시/도 는~~  " + param.value.sido)
  }
  else {
    if(address[0] !=='0' && address[0] !=='') param.value.gugun = attractionStore.getTextByValue(attractionStore.guguns, address[0])
    else param.value.gugun = ''
    console.log("현재 구/군 은~~  " + param.value.gugun)
  }
}

const changeCategory = (val) => {
  param.value.content_type_id = val
  console.log("현재 카테고리는~~  " + param.value.content_type_id)
}

const changeRadius = (val) => {
  param.value.radius = val
  console.log("현재 반경은~~  " + param.value.radius)
}

const changeWord = (val) => {
  param.value.word = val
  console.log("현재 검색어는~~  " + param.value.word)
}

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!")
  currentPage.value = val
  param.value.page = val
  getHotPlaceList()
}

</script>

<template>
  <div class="bg-gray-100 min-h-[58rem]">
      <div class="flex flex-col max-w-screen-xl mx-auto p-4 space-x-4">
        <div class="ml-4 flex justify-between">
          <VToggle v-model="isChecked" name="현재 위치에서 검색하기" class="mt-2"></VToggle>
          <div class="w-[10rem] mb-2">
            <VSelect type="Box" :select-option="order"></VSelect>
          </div>
        </div>
        <div class="block w-full h-24 p-2 ml-1 grid grid-cols-6 relative divide-x-[3px] bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-70">
          <PositionFilter @onAddressSelect="changeAddress" @onRadiusSelect="changeRadius" length="Long" :search-by-location="isChecked"></PositionFilter>
          <div class="col-span-1 relative px-6">
            <span class="font-bold text-xs text-gray-500 dark:text-gray-400">카테고리</span>
            <CategoryFilter @onSelect1="changeCategory" :categories="attractionStore.categories" type="Select"></CategoryFilter>
          </div>
          <div class="col-span-2 relative px-6">
            <VSearch class="mt-3" @clickButton="changeWord"></VSearch>
          </div>

        </div>
        <HotPlaceList :current-page="currentPage" :total-page="totalPage"></HotPlaceList>
        <VPageNavigation class="mt-8" :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
      </div>
    </div>
</template>

<style scoped>

</style>
