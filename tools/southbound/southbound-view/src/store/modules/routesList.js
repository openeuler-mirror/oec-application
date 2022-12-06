const routesListModule = {
  namespaced: true,
  state: {
    routesList: [],
    dynamicRoutes: []
  },
  mutations: {
    // 设置路由菜单
    getRouteList(state, data) {
      state.routesList = data;
    },
    ADD_PAGE(state, page) {
      state.routesList.push(page);
    },
    REMOVE_PAGE(state, i) {
      state.routesList.splice(i, 1);
    },
    CLEAR_PAGE(state) {
      state.routesList = [];
    },
    SET_DYNAMICROUTES(state, dynamicRoutes) {
      state.dynamicRoutes = dynamicRoutes;
    }
  },
  actions: {
    // 设置路由菜单
    getRouteList({ commit }, data) {
      commit('getRouteList', data);
    }
  }
};

export default routesListModule;
