import service from '@/common/utils/request.js';

// 新增
export function add(data) {

  return service({
    url: '/chip-factory/add',
    method: 'post',
    data
  });
}

// 编辑
export function edit(data) {
  return service({
    url: '/chip-factory/update',
    method: 'put',
    data
  });
}

// 删除
export function doDelete(ids) {
  return service({
    url: '/chip-factory/delete',
    method: 'delete',
    params: {
      ids
    }
  });
}

// 从芯片厂商表 获取芯片厂商列
export function getChipFactoryNames() {
  return service({
    url: '/chip-factory/queryNames',
    method: 'get'
  });
}

// 根据芯片厂商 获取芯片型号
export function getChipFactoryModel(chipFactory) {
  return service({
    url: '/chip-factory/queryChipModelName',
    method: 'get',
    params: {
      chipFactory
    }
  });
}

// 获取芯片厂商列表all
export function getChipAllList() {
  return service({
    url: '/chip-factory/export',
    method: 'get'
  });
}
