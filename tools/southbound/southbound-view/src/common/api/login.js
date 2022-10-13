import service from '@/common/utils/request.js';

// 查看系统状态
export function getAdminStatus() {
  return service({
    url: '/user/adminStatus',
    method: 'get'
  });
}

// 添加管理员
export function addAdmin(params) {
  return service({
    url: '/user/addAdmin',
    method: 'post',
    params
  });
}

// 登录
export function login(data) {
  return service({
    url: '/user/login',
    method: 'post',
    params: data
  });
}

// 修改密码
export function modifyPassword(params) {
  return service({
    url: '/user/password',
    method: 'put',
    params
  });
}

// 退出
export function logout() {
  return service({
    url: '/user/logout',
    method: 'get'
  });
}