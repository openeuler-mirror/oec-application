import service from '@/common/utils/request.js';

// 新增
export function add(data) {
  return service({
    url: '/board-factory/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/board-factory/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/board-factory/delete',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 获取板卡类型列表
export function getBoardTypeAll() {
  return service({
    url: '/board-factory/queryBoardType',
    method: 'get'
  });
}

// 获取芯片厂商列表
export function getChipFactory() {
  return service({
    url: '/board-factory/queryChipFactory',
    method: 'get'
  });
}

// 根据芯片厂商获取板卡类型列表
export function getBoardType(chipFactory) {
  return service({
    url: '/board-factory/queryBoardType',
    method: 'get',
    params: {
      chipFactory
    }
  });
}

// 根据芯片厂商/板卡类型获取芯片型号列表
export function getChipModel(chipFactory, boardType) {
  return service({
    url: '/board-factory/queryChipModel',
    method: 'get',
    params: {
      chipFactory,
      boardType
    }
  });
}

// 获取板卡厂商列表all
export function getAllList() {
  return service({
    url: '/board-factory/export',
    method: 'get'
  });
}