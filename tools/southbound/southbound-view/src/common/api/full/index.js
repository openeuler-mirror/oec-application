import service from '@/common/utils/request.js';

// 获取操作系统列表
export function getVersionAll() {
  return service({
    url: '/version-plan/queryVersionList',
    method: 'get'
  });
}

// 获取cpu架构列表
export function getArchitecturesAll() {
  return service({
    url: 'cpu-factory/queryArchitectures',
    method: 'get'
  });
}

// 获取cpu厂商列表
export function getCpuFactoryAll() {
  return service({
    url: 'cpu-factory/queryNames',
    method: 'get'
  });
}

// 获取cpu代次列表
export function getCpuModelAll() {
  return service({
    url: 'cpu-factory/queryModels',
    method: 'get'
  });
}

// 获取整机厂商 name 列表
export function getWholeNames() {
  return service({
    url: '/whole-factory/queryNames',
    method: 'get'
  });
}

// 根据整机厂商获取 cpu 厂商列表
export function getCpuFactory(wholeFactory) {
  return service({
    url: '/whole-factory/queryCpuFactory',
    method: 'get',
    params: {
      wholeFactory
    }
  });
}

// 根据整机厂商和cpu厂商获取 cpu 代次列表
export function getCpuModel(wholeFactory, cpuFactory) {
  return service({
    url: '/whole-factory/queryCpuModel',
    method: 'get',
    params: {
      wholeFactory,
      cpuFactory
    }
  });
}

// 根据cpu代次和操作系统判断是否支持
export function cpuModelIsSupport(versionId, cpuModel) {
  return service({
    url: '/cpu-factory/isSupport',
    method: 'get',
    params: {
      versionId,
      cpuModel
    }
  });
}

// 新增
export function add(data) {
  return service({
    url: '/whole-machine/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/whole-machine/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/whole-machine/deleteBatch',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 获取整机列表
export function getAllList() {
  return service({
    url: '/whole-machine/export',
    method: 'get'
  });
}