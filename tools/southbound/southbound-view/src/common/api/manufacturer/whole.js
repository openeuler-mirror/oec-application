import service from '@/common/utils/request.js';

// 新增
export function add(data) {
  return service({
    url: '/whole-factory/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/whole-factory/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/whole-factory/delete',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 获取整机厂商列表
export function getAllList() {
  return service({
    url: '/whole-factory/export',
    method: 'get'
  });
}