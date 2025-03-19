import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listTag(param, success, fail) {
  local.get(`/api/tags`, { params: param }).then(success).catch(fail);
}

export { listTag };
