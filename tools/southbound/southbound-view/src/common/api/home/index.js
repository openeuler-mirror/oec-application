import service from '@/common/utils/request.js';

// 获取顶部卡片区域列表
export function queryCardList() {
  return service({
    url: '/main-home/queryCardList',
    method: 'get'
  });
}

// 获取整机厂商对应机型信息
export function queryWholeModels() {
  return service({
    url: '/main-home/queryWholeModels',
    method: 'get'
  });
}

// 获取整机厂商对应已适配机型信息
export function queryWholeModelSupports() {
  return service({
    url: '/main-home/queryWholeModelSupports',
    method: 'get'
  });
}

// 获取板卡厂商所有厂商对应机型信息
export function queryBoardModels() {
  return service({
    url: '/main-home/queryBoardModels',
    method: 'get'
  });
}

// 获取板卡厂商对应已适配机型信息
export function queryBoardModelSupports() {
  return service({
    url: '/main-home/queryBoardModelSupports',
    method: 'get'
  });
}

// 获取整机操作系统各阶段适配占比
export function queryVersionWholeDetail(versionId) {
  return service({
    url: '/main-home/queryVersionWholeDetail',
    method: 'get',
    params: {
      versionId
    }
  });
}

// 获取板卡操作系统各阶段适配占比
export function queryVersionBoardDetail(versionId) {
  return service({
    url: '/main-home/queryVersionBoardDetail',
    method: 'get',
    params: {
      versionId
    }
  });
}