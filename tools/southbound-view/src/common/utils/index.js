/**
 * 判断是不是手机号
 * @param {string} phone 
 * @returns boolean
 */
export const isPhone = (phone) => {
  const regex = /^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/;
  return regex.test(phone);
};
/**
 * 判断是不是邮箱
 * @param {string} email 
 * @returns boolean
 */
export const isEmail = (email) => {
  const reg = /^([a-zA-Z]|[0-9])(\w|-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
  return reg.test(email);
};

/**
 * 判断是不是数字、下划线、字母
 * @param {} value 
 * @returns 
 */
export const isNumberletter = (value) =>  {
  const reg = /^[a-zA-Z_0-9]+$/;
  return reg.test(value);
};

/**
 * 判断是否包含中文
 */
export const isIncludeCh = (value) => {
  const reg = /.*[\u4e00-\u9fa5]+.*$/;
  return reg.test(value);
};

/**
 * 格式化日期
 * @param {string} phone
 * @param {string} fmt 
 * @returns string
 */
export const formatDate = (date, fmt) => { 
  const o = {
    'M+': date.getMonth() + 1, 
    'd+': date.getDate(), 
    'h+': date.getHours(), 
    'm+': date.getMinutes(), 
    's+': date.getSeconds(), 
    'S': date.getMilliseconds() 
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  for (let k in o)
    if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
  return fmt;
};
