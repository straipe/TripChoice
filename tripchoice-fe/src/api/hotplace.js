import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listHotPlace(param, success, fail) {
  local.get(`/api/hotplaces`, { params: param }).then(success).catch(fail);
}

function registHotPlace(hotPlace, success, fail) {
  console.log("핫플레이스 등록 중 ....", hotPlace);
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local
    .post(`/api/hotplaces`, JSON.stringify(hotPlace))
    .then(success)
    .catch(fail);
}

function modifyHotPlace(hotPlace, success, fail) {
  console.log("핫플레이스 수정 중 ....", hotPlace);
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local
    .put(`/api/hotplaces`, JSON.stringify(hotPlace))
    .then(success)
    .catch(fail);
}

export { listHotPlace, registHotPlace, modifyHotPlace };
