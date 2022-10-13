import service from '@/common/utils/request.js';

// 芯片厂商维度
export function getChipFactoryStatus(versionName) {
  return service ({
    url: '/board-status/chip-factory',
    method: 'get',
    params: {versionName}
  });
}

// 芯片型号维度
export function getChipModelStatus(versionName) {
  return service ({
    url: '/board-status/chip-model',
    method: 'get',
    params: {versionName}
  });
}

// 板卡维度
export function getBoardTypeStatus(versionName) {
  return service ({
    url: '/board-status/board-type',
    method: 'get',
    params: {versionName}
  });
}

// 版本维度
export function getOsVersionStatus(versionName) {
  return service ({
    url: '/board-status/os-version',
    method: 'get',
    params: {versionName}
  });
}

// 架构维度
export function getArchStatus(versionName) {
  return service ({
    url: '/board-status/architecture',
    method: 'get',
    params: {versionName}
  });
}