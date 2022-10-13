import service from '@/common/utils/request.js';

// 日志列表
export function getAllList(params) {
  return service ({
    url: '/log/operate',
    method: 'get',
    params
  });
}

// 下载日志
export function getLogExcel(params) {
  return service ({
    url: '/log/excel/export',
    method: 'get',
    params
  });
}