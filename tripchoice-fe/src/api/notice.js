import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listNotice(param, success, fail) {
  local.get(`/api/boards`, { params: param }).then(success).catch(fail);
}

function detailNotice(boardId, success, fail) {
  local.get(`/api/boards/${boardId}`).then(success).catch(fail);
}

function registNotice(board, success, fail) {
  console.log("boardsjs board", board);
  local.defaults.headers["Authorization"] = "Bearer "+ sessionStorage.getItem("accessToken");
  local.post(`/api/boards`, JSON.stringify(board)).then(success).catch(fail);
}

function getModifyNotice(boardId, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer "+ sessionStorage.getItem("accessToken");
  local.get(`/api/boards/${boardId}`).then(success).catch(fail);
}

function modifyNotice(board, success, fail) {
  local.defaults.headers["Authorization"] = "Bearer "+ sessionStorage.getItem("accessToken");
  local.put(`/api/boards/${board.boardId}`, JSON.stringify(board)).then(success).catch(fail);
}

function deleteNotice(boardId, success, fail) {
  local.delete(`/api/boards/${boardId}`).then(success).catch(fail);
}

export {
  listNotice,
  detailNotice,
  registNotice,
  getModifyNotice,
  modifyNotice,
  deleteNotice,
};
