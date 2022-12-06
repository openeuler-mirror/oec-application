import service from '@/common/utils/request.js';

// 整机厂商维度
export function getWholeFactoryStatus(versionName) {
  return service ({
    url: '/whole-status/whole-factory',
    method: 'get',
    params: {versionName}
  });
}

// cpu厂商维度
export function getCpuFactoryStatus(versionName) {
  return service ({
    url: '/whole-status/cpu-factory',
    method: 'get',
    params: {versionName}
  });
}

// cpu型号维度
export function getCpuModelStatus(versionName) {
  return service ({
    url: '/whole-status/cpu-model',
    method: 'get',
    params: {versionName}
  });
}

// 架构维度
export function getArchStatus(versionName) {
  return service ({
    url: '/whole-status/architecture',
    method: 'get',
    params: {versionName}
  });
}

// 版本维度
export function getOsVersionStatus(versionName) {
  return service ({
    url: '/whole-status/os-version',
    method: 'get',
    params: {versionName}
  });
}