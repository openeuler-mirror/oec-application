import { createRouter, createWebHashHistory } from 'vue-router';
import { staticRoutes, dynamicRoutes } from '@/router/modules/staticRoutes.js';
import store from '@/store/index';

const router = createRouter({
  history: createWebHashHistory(),
  routes: staticRoutes,
  dynamicHandled: false
});

const whiteList = ['/login', '/404'];

// 添加动态路由
router.addRoute(dynamicRoutes[0]);
// 路由加载前
router.beforeEach((to, from, next) => {
  if (!getToken()) {
    if (whiteList.includes(to.path)) {
      next();
    } else {
      next('/login');
    }
  } else {
    if (!router.options.dynamicHandled) {
      let routerList = getDynamicRouter(dynamicRoutes[0], store.state.userInfoModule.userInfo.role);
      let dynamicRouteObj = {
        path: '/',
        name: 'homemenu',
        component: () => import('@/layout/main/classic.vue'),
        redirect: '/home'
      };
      dynamicRouteObj.children = routerList;
      store.commit('routesListModule/SET_DYNAMICROUTES', routerList);
      router.addRoute(dynamicRouteObj);
      next({...to, replace: true });
      router.options.dynamicHandled = true;
    } else {
      next();
    }
  }
  if (getTabRouter(to.path) && to.meta.title){
    store.commit('routesListModule/ADD_PAGE', to);
  }
});

const getDynamicRouter = (routers, role) => {
  if (role === 0) {
    return routers.children;
  }
  return routers.children.filter(item => item.meta.role.includes(role));
};

const getToken = () => {
  return store.state.userInfoModule.token;
};
/**
 *  判断store中routesList是否存在当前要跳转的路由，如果不存在则添加，反之不用添加
 * @param {*} toPath 
 * @returns 
 */
const getTabRouter = (toPath)=>{
  let menuStore = store.state.routesListModule.routesList;
  for(let item of menuStore){
    if (item['path'] === toPath){
      return 0;
    }
  }
  return 1;
};

// 导出路由
export default router;
