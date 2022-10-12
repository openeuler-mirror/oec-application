import service from '@/common/utils/request.js';

// 兼容性清单中新增的整机机型
export function getCommunityWholeData(startTime, endTime) {
  return service({
    url: '/community-data/whole',
    method: 'get',
    params: {
      startTime,
      endTime
    }
  });
}

// 兼容性清单中新增的板卡型号
export function getCommunityBoardData(startTime, endTime) {
  return service({
    url: '/community-data/board',
    method: 'get',
    params: {
      startTime,
      endTime
    }
  });
}