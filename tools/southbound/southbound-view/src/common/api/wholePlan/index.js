import service from '@/common/utils/request.js';

// 整机计划列表
export function getAllList(params) {
  return service ({
    url: '/whole-plan/queryAll',
    method: 'get',
    params
  });
}

// 导出时查询全部计划列表
export function getAllListWithExport() {
  return service ({
    url: '/whole-plan/export',
    method: 'get'
  });
}

// 添加整机计划
export function add(data) {
  return service ({
    url: '/whole-plan/add',
    method: 'post',
    data
  });
}

// 编辑整机计划
export function edit(data) {
  return service ({
    url: '/whole-plan/update',
    method: 'put',
    data
  });
}

// 删除整机计划
export function doDelete(ids) {
  return service ({
    url: '/whole-plan/delete',
    method: 'delete',
    params: {ids}
  });
}

// 查询整机厂商名称
export function queryFactoryNames() {
  return service ({
    url: '/whole-factory/queryNames',
    method: 'get'
  });
}

// 查询典型和扩展机型
export function queryModels(wholeFactory) {
  return service ({
    url: '/whole-factory/queryModels',
    method: 'get',
    params: {
      wholeFactory
    }
  });
}

// 查询版本下的适配机型
export function queryVersionModel(versionName) {
  return service ({
    url: '/whole-plan/queryVersionModel',
    method: 'get',
    params: {
      versionName
    }
  });
}

// 根据整机厂商查询支持的OS版本
export function queryVersionListByWholeFactory() {
  return service ({
    url: '/version-plan/queryVersionList',
    method: 'get'
  });
}