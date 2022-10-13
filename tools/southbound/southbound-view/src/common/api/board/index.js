import service from '@/common/utils/request.js';

// 新增
export function add(data) {
  return service({
    url: '/board/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/board/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/board/deleteBatch',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 根据芯片型号和操作系统判断是否支持
export function chipModelIsSupport(chipModel, versionId) {
  return service({
    url: '/chip-factory/isSupport',
    method: 'get',
    params: {
      chipModel,
      versionId
    }
  });
}

// 获取板卡展示列表
export function getAllList() {
  return service({
    url: '/board/export',
    method: 'get'
  });
}