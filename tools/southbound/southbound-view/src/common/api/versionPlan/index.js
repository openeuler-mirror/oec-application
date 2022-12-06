import service from '@/common/utils/request.js';

// 版本计划列表
export function getAllList(params){
  return service ({
    url: '/version-plan/queryAll',
    method: 'get',
    params
  });
}

// 添加版本计划
export function add(data) {
  return service ({
    url: '/version-plan/add',
    method: 'post',
    data: data
  });
}

// 编辑版本计划
export function edit(id, data) {
  return service ({
    url: '/version-plan/update',
    method: 'put',
    data
  });
}

// 删除版本计划
export function doDelete(versionId) {
  return service ({
    url: '/version-plan/delete',
    method: 'delete',
    params: {versionId}
  });
}

// 查询版本
export function queryVersionNames() {
  return service ({
    url: '/version-plan/queryVersionList',
    method: 'get'
  });
}