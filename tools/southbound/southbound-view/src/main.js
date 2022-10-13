
import { createApp } from 'vue';
import App from './App.vue';
import store from './store/index';
import router from './router/index';
import installElementPlus from './plugins/element';
import i18n from '@/i18n/index';
import './styles/index.scss';
import '@wangeditor/editor/dist/css/style.css';

const app = createApp(App);
app.use(store).use(router).use(i18n);
installElementPlus(app);
app.config.globalProperties.$hasAuth = (e) => {
  let role = store.state.userInfoModule.userInfo.role;
  if (role === 0) return true;
  if (!e) return false;
  return e.includes(role);
};
// 挂载
app.mount('#app');
