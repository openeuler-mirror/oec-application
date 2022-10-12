import service from '@/common/utils/request.js';

// 初始化列表
export function initData(url, params) {
  return service({
    url: url,
    method: 'get',
    params
  });
}

// 根据 type 获取筛选条件的列表
export function getFilterList(type) {
  return service({
    url: '/common/filter-list',
    method: 'get',
    params: {
      type
    }
  });
}