import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, userDetail, tokenRegeneration, logout } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref({
    email: "",
    password: "",
    name: "",
    image: "",
  });

  const userRole = ref(null);
  const isValidToken = ref(false);

  const roles = [
    { text: "USER", value: "ROLE_USER" },
    { text: "ADMIN", value: "ROLE_ADMIN" },
  ];

  const userInfoClear = () => {
    userInfo.value.email = "";
    userInfo.value.password = "";
    userInfo.value.name = "";
    userInfo.value.image = "";
  };

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        console.log(response);
        if (response.status === httpStatusCode.OK) {
          console.log("로그인 성공!!!!");
          let { data } = response;
          console.log(data);
          let accessToken = data["accessToken"];
          let refreshToken = data["refreshToken"];
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
        }
      },
      (error) => {
        console.log("로그인 실패!!!!");
        isLogin.value = false;
        isLoginError.value = true;
        isValidToken.value = false;
        console.error(error);
      }
    );
  };

  const getUserInfo = async (token) => {
    console.log("받은 토큰: " + token);
    let decodeToken = jwtDecode(token);
    console.log("해석한 토큰: " + decodeToken);
    await userDetail(
      (response) => {
        console.log("응답 데이터!!: " + response);
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data;
          userRole.value = response.data.role;
          console.log("초기 유저 역할: ");
          console.log(userRole.value);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error(
          "[토큰 만료되어 사용 불가능.] : ",
          error.response.status,
          error.response.statusText
        );
        isValidToken.value = false;

        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["accessToken"];
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfoClear();
              isValidToken.value = false;
              router.push({ name: "user-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfoClear();
            }
          );
        }
      }
    );
  };

  const userLogout = async () => {
    console.log("로그아웃 아이디 : " + userInfo.value.email);
    await logout(
      { refreshToken: sessionStorage.getItem("refreshToken") },
      (response) => {
        if (response.status === httpStatusCode.NOCONTENT) {
          isLogin.value = false;
          userInfoClear();
          isValidToken.value = false;

          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          router.push(`/`);
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    isValidToken,
    roles,
    userInfo,
    userRole,
    userLogin,
    userInfoClear,
    getUserInfo,
    tokenRegenerate,
    userLogout,
  };
});
