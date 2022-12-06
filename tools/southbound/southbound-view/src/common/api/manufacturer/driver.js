import service from '@/common/utils/request.js';

// 新增
export function add(data) {
  return service({
    url: '/driver-manage/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/driver-manage/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/driver-manage/delete',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 获取驱动名称name列表
export function getDriverName(params) {
  return service({
    url: '/driver-manage/query-driver-name',
    method: 'get',
    params
  });
}
// 获取驱动名称name列表
export function getDriverVersion(params) {
  return service({
    url: '/driver-manage/query-driver-version',
    method: 'get',
    params
  });
}

// 获取驱动管理列表all
export function getDriverAllList() {
  return service({
    url: '/driver-manage/export',
    method: 'get'
  });
}