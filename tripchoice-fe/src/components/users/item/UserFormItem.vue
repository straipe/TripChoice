<script setup>
import VButton from "@/components/common/VButton.vue"
import { ref } from "vue"
import { useRouter } from "vue-router";
import { userSignup, modifyInfo } from "@/api/user";
import { useMemberStore } from "@/stores/member";
import { httpStatusCode } from "@/util/http-status"
import VSelect from "@/components/common/VSelect.vue";

const { type } = defineProps({
    type: {
        type: String,
        default: "Profile",
    },
})
const memberStore = useMemberStore();
const router = useRouter()

const onFileChange = (event) => {
  const file = event.target.files[0]
  if(file){
    const reader = new FileReader()
    reader.onload = (e) =>{
      memberStore.userInfo.image = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const createInfo = () =>{
    userSignup(
      memberStore.userInfo,
        (response) => {
            console.log("회원가입 중...")
            if (response.status === httpStatusCode.CREATE) {
                console.log("회원가입 성공!!")
                router.push({'name':'main'})
            }
        },
        (error) => {
            console.log("회원가입 실패....")
            console.error(error);
        }
    )
}

const changeInfo = () => {
    modifyInfo(
        memberStore.userInfo,
        (response) => {
            console.log("회원정보 수정 중...")
            if (response.status === httpStatusCode.NOCONTENT) {
                alert("회원정보가 변경되었습니다.")
                if(memberStore.userRole === memberStore.userInfo.role) router.push({'name':'main'})
                else {
                  alert("역할이 변경되었습니다. 재로그인 해주세요.")
                  memberStore.userLogout()
                }
            }
        },
        (error) => {
            console.log("회원정보 수정 실패....")
            console.error(error);
            console.log(memberStore.userInfo)
        }
    )
}

const changeRole = (role) =>{
  memberStore.userInfo.role = role
}
</script>

<template>
    <div>
        <div v-if="type==='Join'" class="w-[48rem] h-auto bg-white p-6 border border-gray-200 rounded-lg shadow">
            <form class="max-w-lg mx-auto" @submit.prevent="createInfo">
                <label for="img" class="block mb-2 text-lg text-center font-extrabold text-gray-900 dark:text-white mb-5">프로필 사진</label>
                <div class="flex items-center justify-center w-full mb-8">
                    <label v-if="memberStore.userInfo.image ==='' " for="img" class="flex flex-col items-center justify-center w-[15rem] h-[15rem] border-2 border-gray-300 border-dashed rounded-full cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                        <div class="flex flex-col items-center justify-center pt-5 pb-6">
                            <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                            </svg>
                            <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                            <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                        </div>
                        <input id="img" type="file" class="hidden" @change="onFileChange"/>
                    </label>
                    <label v-else class="flex flex-col items-center justify-center w-[15rem] h-[15rem] rounded-full cursor-pointer">
                      <img :src="memberStore.userInfo.image" class="w-[15rem] h-[15rem] rounded-full" />
                      <input id="img" type="file" class="hidden" @change="onFileChange"/>
                    </label>
                </div>
                <div class="mb-5">
                    <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이메일</label>
                    <div class="flex">
                        <input type="email" id="email" v-model="memberStore.userInfo.email" class="w-[24rem] h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required />
                        <VButton class="ml-3" type="Code" title="인증하기"></VButton>
                    </div>
                </div>
                <div class="mb-5">
                    <label for="email-code" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이메일 인증 코드</label>
                    <input type="text" id="email-code" class="h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                </div>
                <div class="mb-5">
                    <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">비밀번호</label>
                    <input type="password" id="password" v-model="memberStore.userInfo.password" class="h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                </div>
                <div class="mb-5">
                    <label for="repeat-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">비밀번호 확인</label>
                    <input type="password" id="repeat-password" class="h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                </div>
                <div class="mb-5">
                    <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">닉네임</label>
                    <input type="text" id="username" v-model="memberStore.userInfo.name" class="h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                </div>
                <div class="flex items-start mb-5">
                    <div class="flex items-center h-5">
                    <input id="terms" type="checkbox" value="" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800" required />
                    </div>
                    <label for="terms" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"><a href="#" class="text-blue-600 hover:underline">이용약관</a>에 모두 동의합니다.</label>
                </div>
                <VButton type="Join" title="회원가입하기"></VButton>
            </form>
        </div>
        <div v-if="type==='Profile'" class="w-[48rem] h-auto bg-white p-6 border border-gray-200 rounded-lg shadow">
            <form class="max-w-lg mx-auto" @submit.prevent="changeInfo">
                <label for="img" class="block mb-2 text-lg text-center font-extrabold text-gray-900 dark:text-white mb-5">프로필 사진</label>
                <div class="flex items-center justify-center w-full mb-8">
                    <label v-if="memberStore.userInfo.image ==='' " for="img" class="flex flex-col items-center justify-center w-[15rem] h-[15rem] border-2 border-gray-300 border-dashed rounded-full cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                        <div class="flex flex-col items-center justify-center pt-5 pb-6">
                            <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                            </svg>
                            <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                            <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                        </div>
                        <input id="img" type="file" class="hidden" @change="onFileChange"/>
                    </label>
                    <label v-else class="flex flex-col items-center justify-center w-[15rem] h-[15rem] rounded-full cursor-pointer">
                      <img :src="memberStore.userInfo.image" class="w-[15rem] h-[15rem] rounded-full" />
                      <input id="img" type="file" class="hidden" @change="onFileChange"/>
                    </label>
                </div>
                <div class="mt-5 mb-5">
                    <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이메일</label>
                    <div class="flex">
                        <input type="email" id="email" :value="memberStore.userInfo.email" class="w-full h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-500 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" disabled />
                    </div>
                </div>
                <div class="mt-5 mb-5">
                    <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">닉네임</label>
                    <input type="text" id="username" v-model="memberStore.userInfo.name" class="h-[2.5rem] shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                </div>
                <div class="mb-10">
                    <label for="userRole" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">역할</label>
                    <VSelect type="Box" :selectOption="memberStore.roles" :defaultOption="memberStore.userInfo.role" @on-key-select="changeRole"></VSelect>
                </div>
                <VButton type="Modify" title="회원정보 변경하기"></VButton>
            </form>
        </div>
    </div>
</template>

<style scoped>

</style>
