<script setup>
import AttractionRowList from "@/components/attractions/AttractionRowList.vue";
import PositionFilter from "@/components/attractions/PositionFilter.vue";
import CategoryFilter from "@/components/attractions/CategoryFilter.vue";
import WeatherTodayInfo from "@/components/others/WeatherTodayInfo.vue";
import VButton from "@/components/common/VButton.vue";
import VNumber from "@/components/common/VNumber.vue";
import VMap from "@/components/common/VMap.vue";
import VSearch from "@/components/common/VSearch.vue";
import VToggle from "@/components/common/VToggle.vue"
import leaf from "@/assets/images/leaf.png";
import { ref, watch, onMounted } from "vue"
import { listAttraction, listGuGuns } from "@/api/attraction"
import { useAttractionStore } from "@/stores/attraction";
import { usePlanStore } from "@/stores/plan";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const attractionStore = useAttractionStore()
const planStore = usePlanStore()
const currentPage = ref(1)
const totalPage = ref(5)
const { VITE_ATTRACTION_LIST_SIZE } = import.meta.env
const categories = attractionStore.categories


const param = ref({
  sido_code: '',
  content_type_id: '',
  gugun_code: '',
  radius: 10,
  word: '',
  key: "",
  page: currentPage.value,
  size: VITE_ATTRACTION_LIST_SIZE,
})

onMounted(()=>{
  getAttractionList()
})

const getAttractionList = () =>{
  console.log("서버에서 관광지목록 얻어오자!!!", param.value)
  listAttraction(
    param.value,
    ({ data }) => {
      console.log("관광지 데이터 조회 성공")
      console.log(data)
      attractionStore.attractions = data.content
      currentPage.value = data.currentPage+1
      totalPage.value = data.totalPage
      attractionStore.loading = false
    },
    (error) => {
      console.log(error)
    }
  )
}
const searchByCategory = () =>{
  console.log("searchByCategory 시작!!")
  getAttractionList()
}

const searchByWord = () =>{
  console.log("searchByWord 시작!!")
  param.value.key="word"
  getAttractionList()
}

const searchByAddress = () =>{
  console.log("searchByAddress 시작!!")
  param.value.word=""
  param.value.key="address"
  getAttractionList()
}

const searchByLocation = () =>{
  console.log("searchByLocation 시작!!")
  param.value.word=""
  param.value.key="location"
  getAttractionList()
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

watch( () => param.value.sido_code, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('address')
});

watch( () => param.value.gugun_code, (newVal, oldVal) => {
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
    param.value.sido_code = address[0]
    console.log("현재 시/도 는~~  " + param.value.sido_code)
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
    param.value.gugun_code = address[0]
    console.log("현재 구/군 은~~  " + param.value.gugun_code)
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
  getAttractionList()
}
</script>
<template>
    <div>
        <VMap class="ml-[20%]" style="width: 80%; height: 91.8vh"></VMap>
        <aside id="default-sidebar" class="fixed top-[84px] left-0 z-40 w-1/5 h-screen shadow-xl transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
          <div class="h-full overflow-y-auto bg-gray-50 dark:bg-gray-800">
            <div class="relative">
              <img class="absolute w-full h-[21rem]" :src="leaf"/>
              <WeatherTodayInfo type="Text"></WeatherTodayInfo>
              <VToggle v-model="isChecked" name="현재 위치에서 검색하기" class="ml-4 relative"></VToggle>
              <div class="relative flex flex-col items-center">
                  <PositionFilter @onAddressSelect="changeAddress" @onRadiusSelect="changeRadius" :search-by-location="isChecked" length="Short"></PositionFilter>
                  <div class="block relative w-[26rem] h-[6rem] p-2">
                    <label for="default-search"
                        class="block ml-5 mb-2 text-sm font-bold text-gray-900 dark:text-white">검색</label>
                    <VSearch @clickButton="changeWord"></VSearch>
                  </div>
              </div>
            </div>
            <AttractionRowList mode="Search"></AttractionRowList>
            <VPageNavigation class="m-3" :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
          </div>
        </aside>
        <aside id="plan-sidebar" class="fixed top-[84px] left-[30rem] z-30 w-1/5 h-screen shadow-xl transition-transform"
          :class="planStore.isOpen ? '-translate-x-[3.7rem]' : '-translate-x-[104%]'" aria-label="Sidebar">
          <div class="h-full bg-white shadow-lg overflow-y-auto">
            <div class="flex justify-between m-8">
              <h2 class="text-2xl font-bold text-black">여행 일정</h2>
              <div class="flex">
                <label for="counter-input" class="block mt-1 mr-3 text-sm font-medium text-gray-500">여행 일수: </label>
                <VNumber class="mr-3"></VNumber>
              </div>
            </div>
            <div class="overflow-y-auto scrollbar-thin h-[80%]">
              <AttractionRowList v-for="num in planStore.day" :key="num" mode="DayPlan" :num="num"></AttractionRowList>
              <VButton type="PlanWrite" title="개요 작성하기" class="ml-8 mt-8"></VButton>
            </div>
          </div>
        </aside>
        <img :src="planStore.dynamicImg()" @click="planStore.toggleSidebar" class="fixed top-[calc(91.5vh/2)] left-[27.5rem] w-8 h-8 z-50 cursor-pointer transition-transform"
        :class="planStore.isOpen ? 'translate-x-[23.3rem]' :'-translate-x-3'"/>
        <CategoryFilter @onSelect1="changeCategory" :categories="categories" type="Radio" :inherit="planStore.dynamicInherit"></CategoryFilter>
    </div>
</template>

<style scoped>

</style>
