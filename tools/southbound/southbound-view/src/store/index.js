import { createStore } from 'vuex';
import routesListModule from './modules/routesList';
import userInfoModule from './modules/userInfo';


export default createStore({
  modules: {
    routesListModule,
    userInfoModule
  }
});

