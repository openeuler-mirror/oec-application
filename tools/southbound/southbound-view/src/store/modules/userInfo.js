const userInfoModule = {
  namespaced: true,
  state: {
    token: sessionStorage.getItem('token') || '',
    userInfo: sessionStorage.getItem('userInfo') ? JSON.parse(sessionStorage.getItem('userInfo')) : {}
  },
  mutations: {
    // 设置路由菜单
    SET_TOKEN(state, token) {
      state.token = token;
      sessionStorage.setItem('token', token);
    },
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo;
      sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
    },
    CLEAR(state) {
      state.token = '';
      state.userInfo = {};
      sessionStorage.clear();
    }
  },
  actions: {
    // 设置路由菜单
    set_token({ commit }, token) {
      commit('SET_TOKEN', token);
    }
  }
};

export default userInfoModule;
