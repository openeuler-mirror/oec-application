import service from '@/common/utils/request.js';

// 板卡计划列表
export function getAllList(params) {
  return service ({
    url: '/board-plan/queryAll',
    method: 'get',
    params
  });
}

// 导出时查询全部计划列表
export function getAllListWithExport() {
  return service ({
    url: '/board-plan/export',
    method: 'get'
  });
}

// 添加板卡计划
export function add(data) {
  return service ({
    url: '/board-plan/add',
    method: 'post',
    data
  });
}

// 编辑板卡计划
export function edit(data) {
  return service ({
    url: '/board-plan/update',
    method: 'put',
    data
  });
}

// 删除板卡计划
export function doDelete(ids) {
  return service ({
    url: '/board-plan/delete',
    method: 'delete',
    params: {ids}
  });
}

// 查询芯片厂商对应的典型和扩展板卡
export function queryModels(chipFactory, versionId) {
  return service ({
    url: '/board-factory/queryModels',
    method: 'get',
    params: {
      chipFactory,
      versionId
    }
  });
}

// 查询版本下的适配机型
export function queryVersionModel(versionName) {
  return service ({
    url: '/board-plan/queryVersionModel',
    method: 'get',
    params: {
      versionName
    }
  });
}

//根据芯片厂商查询支持的os版本
export function queryVersionListByChipFactory() {
  return service ({
    url: '/version-plan/queryVersionList',
    method: 'get'
  });
}