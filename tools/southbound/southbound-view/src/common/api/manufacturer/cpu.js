import service from '@/common/utils/request.js';

// 新增
export function add(data) {
  return service({
    url: '/cpu-factory/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/cpu-factory/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/cpu-factory/delete',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 根据cpu厂商表 获取 cpu 厂商列表
export function getCpuFactoryNames() {
  return service({
    url: '/cpu-factory/queryNames',
    method: 'get'
  });
}

// 根据cpu厂商列 获取 cpu 代次列表
export function getCpuFactoryModel(cpuFactory) {
  return service({
    url: '/cpu-factory/queryCpuModelNames',
    method: 'get',
    params: {
      cpuFactory
    }
  });
}

// 获取CPU厂商列表all
export function getAllList() {
  return service({
    url: '/cpu-factory/export',
    method: 'get'
  });
}