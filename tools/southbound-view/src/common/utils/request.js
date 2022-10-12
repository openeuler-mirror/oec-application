import axios from 'axios';
import router from '@/router';
import store from '@/store';
import { ElMessage } from 'element-plus';
let flag = false;

const messageStatus = {
  ECONNABORTED: '请求超时，请稍后重试',
  ERR_NETWORK: '网络错误',
  ERR_BAD_RESPONSE: '服务端错误',
  ERR_BAD_REQUEST: '资源不存在'
};

const service = axios.create({
  baseURL: process.env.VUE_APP_BASEURL,
  timeout: 20000
});

// 添加请求拦截器
service.interceptors.request.use(
  (config) => {
    let token = sessionStorage.getItem('token');
    config['headers'].token = token;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 添加响应拦截器
service.interceptors.response.use(
  (res) => {
    const resp = { code: 200, data: null };
    if (res.status === 200 || res.status === 304) {
      let code = Number(res.data.code);
      if (code === 200) {
        resp.data = res.data.data;
      } else {
        resp.code = code;
        handlerError({info: res.data.msg});
        if (code === 401) {
          store.commit('userInfoModule/CLEAR');
          store.commit('routesListModule/CLEAR_PAGE');
          router.options.dynamicHandled = false;
          router.replace('/login');
        }       
      }
    } 
    return resp;
  },
  (error) => {
    let code = error.code;
    if (messageStatus[code]) {
      error.info = messageStatus[code];
    } else {
      error.info = error.message || '服务器错误';
    }
    handlerError(error);
    return Promise.reject(error);
  }
);

function handlerError(error) {
  if (!flag) {
    flag = true;
    ElMessage({
      type: 'error',
      grouping: 'true',
      message: error.info,
      onClose: () => {
        flag = false;
      }
    });
  }  
}

export default service;