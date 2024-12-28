import { localAxios } from "@/util/http-commons";

const local = localAxios();

function userSignup(user, success, fail) {
  local
    .post(`/api/users/signup`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}
function userInfo(success, fail) {
  local.post(`/api/users/me`).then(success).catch(fail);
}
function modifyInfo(user, success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local.patch(`/api/users/me`, JSON.stringify(user)).then(success).catch(fail);
}
function userSignout(success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local.post(`/api/users/me`).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
  await local.post(`/api/auth/login`, param).then(success).catch(fail);
}

async function userDetail(success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  await local.get(`/api/users/me`).then(success).catch(fail);
}

async function tokenRegeneration(success, fail) {
  local.defaults.headers["refreshToken"] =
    "Bearer " + sessionStorage.getItem("refreshToken");
  await local.post(`/api/auth/refresh`).then(success).catch(fail);
}

async function logout(refreshToken, success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  await local.post(`/api/auth/logout`, refreshToken).then(success).catch(fail);
}

export {
  userConfirm,
  userSignup,
  userSignout,
  userInfo,
  modifyInfo,
  userDetail,
  tokenRegeneration,
  logout,
};
