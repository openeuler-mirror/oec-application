import service from '@/common/utils/request.js';

// 系统用户列表
export function getAllList(params) {
  return service ({
    url: '/user/queryAll',
    method: 'get',
    params
  });
}

// 添加系统用户
export function add(data) {
  return service ({
    url: '/user/add',
    method: 'post',
    data
  });
}

// 编辑系统用户
export function edit(data) {
  return service ({
    url: '/user/update',
    method: 'put',
    data
  });
}

// 删除系统用户
export function doDelete(ids) {
  return service ({
    url: '/user/delete',
    method: 'delete',
    params: {ids}
  });
} 