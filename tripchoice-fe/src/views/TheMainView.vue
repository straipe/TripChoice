<script setup>
import CategoryFilter from "@/components/attractions/CategoryFilter.vue"
import PositionFilter from "@/components/attractions/PositionFilter.vue"
import AttractionCardList from "@/components/attractions/AttractionCardList.vue"
import PlanCardList from "@/components/plans/PlanCardList.vue"
import WeatherTodayInfo from "@/components/others/WeatherTodayInfo.vue"
import WeatherWeekInfo from "@/components/others/WeatherWeekInfo.vue"
import GPTTagList from "@/components/others/GPTTagList.vue"
import VButton from "@/components/common/VButton.vue"
import VToggle from "@/components/common/VToggle.vue"
import VPageNavigation from "@/components/common/VPageNavigation.vue"
import { listAttraction, listGuGuns, listTop4Attraction } from "@/api/attraction"
import { listTag } from "@/api/gpt"
import { listTop4Plan } from "@/api/plan"
import { useAttractionStore } from "@/stores/attraction"
import { ref, watch, onMounted } from 'vue'
import { usePlanStore } from "@/stores/plan"

const attractionStore = useAttractionStore()
const planStore = usePlanStore()

const categories = attractionStore.categories
const currentPage = ref(1)
const totalPage = ref(4)
const VITE_ARTICLE_LIST_SIZE = 6

const isLoading = ref(true)
const isLoadingTop4Attr = ref(true)
const isLoadingTop4Plan = ref(true)
const isLoadingTag = ref(true)
const isChecked = ref(false)

const param = ref({
  sido_code: '',
  content_type_id: '',
  gugun_code: '',
  radius: 10,
  word: '',
  key: "",
  page: currentPage.value,
  size: VITE_ARTICLE_LIST_SIZE,
})

const tagList = ref(['#코엑스몰','#별마당도서관','#가로수길','#압구정로데오','#봉은사','#선릉과정릉','#청담동','#삼성동','#신사동','#역삼동','#논현동','#도산공원'])

onMounted(()=>{
    getAttractionList()
    getTop4AttractionList()
    getTop4PlanList()
    //getTagList()
  }
)


const getTagList = () =>{
  console.log("GPT를 활용한 태그 불러오기!!!")
  isLoadingTag.value = true
  listTag(
    { address: "서울 강남구", model: "gpt-4" },
    ({ data }) => {
      console.log(data)
      tagList.value = data.content
      isLoadingTag.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const getAttractionList = () =>{
  console.log("서버에서 관광지목록 얻어오자!!!", param.value)
  isLoading.value = true
  listAttraction(
    param.value,
    ({ data }) => {
      console.log("받은 데이터: ")
      console.log(data)
      attractionStore.attractions = data.content
      currentPage.value = data.currentPage+1
      totalPage.value = data.totalPage
      isLoading.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const getTop4AttractionList = () => {
  console.log("Top4 관광지 목록 조회")
  isLoadingTop4Attr.value = true
  listTop4Attraction(
    ({ data }) => {
      console.log("Top4 관광지 : ")
      console.log(data)
      attractionStore.top4Attractions = data.content
      isLoadingTop4Attr.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const getTop4PlanList = () => {
  console.log("Top4 플랜 목록 조회")
  isLoadingTop4Plan.value = true
  listTop4Plan(
    ({ data }) => {
      console.log("Top4 플랜 : ")
      console.log(data)
      planStore.top4Plans = data.content
      isLoadingTop4Plan.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const searchByAddress = () =>{
  console.log("searchByAddress 시작!!")
  param.value.key="address"
  getAttractionList()
}

const searchByLocation = () =>{
  console.log("searchByLocation 시작!!")
  param.value.key="location"
  getAttractionList()
}

const searchByCategory = () =>{
  console.log("searchByCategory 시작!!")
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
    else searchByCategory()
  }, 1000);
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!")
  currentPage.value = val
  param.value.page = val
  getAttractionList()
}

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

watch( () => param.value.content_type_id, (newVal, oldVal) => {
  console.log("param 변경 감지!", newVal, oldVal);
  onParamChange('category')
});


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

</script>

<template>
    <div class="bg-gray-100 min-h-[58rem]">
        <div class="flex max-w-screen-xl mx-auto p-4 space-x-4">
          <div class="flex flex-col">
            <WeatherTodayInfo type="Bar"></WeatherTodayInfo>
            <WeatherWeekInfo></WeatherWeekInfo>
            <GPTTagList :tags="tagList"></GPTTagList>
          </div>
          <div>
            <div class="flex justify-between">
              <VToggle v-model="isChecked" name="현재 위치에서 검색하기" class="mt-6"></VToggle>
              <VButton class="mt-6" type="Map" title="지도로 이동하기"></VButton>
            </div>
            <div>
              <div class="block w-[50rem] h-24 p-2 grid grid-cols-4 relative divide-x-[3px] bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mt-3">
                <PositionFilter @onAddressSelect="changeAddress" @onRadiusSelect="changeRadius" :search-by-location="isChecked" length="Long"></PositionFilter>
                <div class="col-span-1 relative px-4">
                  <span class="font-bold text-xs text-gray-500 dark:text-gray-400">카테고리</span>
                  <CategoryFilter @onSelect1="changeCategory" :categories="categories" type="Select"></CategoryFilter>
                </div>
              </div>
              <div v-if="isLoading" role="status" class="grid place-content-center h-[36rem]">
                  <svg aria-hidden="true" class="w-16 h-16 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                      <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                  </svg>
                  <span class="sr-only">Loading...</span>
              </div>
              <div v-else>
                <AttractionCardList name="Reduction" :attractions="attractionStore.attractions"></AttractionCardList>
                <VPageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPageNavigation>
              </div>
            </div>
          </div>
        </div>

        <div class="max-w-screen-xl mx-auto p-4">
            <p class="text-3xl font-bold text-gray-900 mb-4">
              <span class="text-red-500">TOP4</span> 인기 여행 계획
            </p>
            <PlanCardList :plans="planStore.top4Plans" mode="Main"></PlanCardList>
        </div>
        <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
            <p class="text-3xl font-bold text-gray-900 mb-4">
              <span class="text-red-500">TOP4</span> 인기 관광지
            </p>
            <AttractionCardList :attractions="attractionStore.top4Attractions"></AttractionCardList>
        </div>
    </div>
</template>

<style scoped>

</style>
