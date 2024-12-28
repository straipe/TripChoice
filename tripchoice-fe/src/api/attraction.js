import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listAttraction(param, success, fail) {
  local.get(`/api/attractions`, { params: param }).then(success).catch(fail);
}

function detailAttraction(attractionId, success, fail) {
  local.get(`/api/attractions/${attractionId}`).then(success).catch(fail);
}

function listGuGuns(sidoId, success, fail) {
  local.get(`/api/attractions/areas/${sidoId}`).then(success).catch(fail);
}

function listTop4Attraction(success, fail) {
  local.get(`/api/attractions/top4`).then(success).catch(fail);
}

export { listAttraction, detailAttraction, listGuGuns, listTop4Attraction };
