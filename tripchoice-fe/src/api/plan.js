import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listPlan(param, success, fail) {
  local.get(`/api/plans`, { params: param }).then(success).catch(fail);
}

function detailPlan(planId, success, fail) {
  local.get(`/api/plans/${planId}`).then(success).catch(fail);
}

function registPlan(plan, success, fail) {
  console.log("plan 등록!!!", plan);
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local.post(`/api/plans`, plan).then(success).catch(fail);
}

function modifyPlan(plan, success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local
    .put(`/api/plans/${plan.id}`, JSON.stringify(plan))
    .then(success)
    .catch(fail);
}

function deletePlan(planId, success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local.delete(`/api/plans/${planId}`).then(success).catch(fail);
}

function listTop4Plan(success, fail) {
  local.get(`/api/plans/top4`).then(success).catch(fail);
}

export {
  listPlan,
  listTop4Plan,
  detailPlan,
  registPlan,
  modifyPlan,
  deletePlan,
};
