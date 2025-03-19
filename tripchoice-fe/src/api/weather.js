import { localAxios } from "@/util/http-commons";

const local = localAxios();

function nowWeather(param, success, fail) {
  local.get(`/api/weather/now`, { params: param }).then(success).catch(fail);
}

function listWeather(param, success, fail) {
  local.get(`/api/weather/list`, { params: param }).then(success).catch(fail);
}

export { nowWeather, listWeather };
