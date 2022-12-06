// 这里是静态路由主要配置登录、首页以及404页面路由的配置
export const staticRoutes = [
  {
    path: '/:pathMatch(.*)*',
    name: 'notFound',
    component: () => import('@/views/404.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  }
];

// 定义动态路由
export const dynamicRoutes = [
  {
    path: '/',
    name: 'homemenu',
    component: () => import('@/layout/main/classic.vue'),
    redirect: '/home',
    meta: {
      title: '菜单'

    },
    children: [
      // 首页
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/home/index.vue'),
        meta: {
          title: '首页',
          isHide: false,
          role: [0, 1, 2]
        }
      },
      // 全景展示
      {
        path: '/panoramic',
        name: 'panoramic',
        component: () => import('@/layout/main.vue'),
        redirect: '/panoramic/whole',
        meta: {
          title: '全景展示',
          isHide: false,
          role: [0, 1, 2]
        },
        children: [
          {
            path: '/panoramic/whole',
            name: 'whole',
            component: () => import('@/views/panoramic/whole/index.vue'),
            meta: {
              title: '整机展示',
              isHide: false
            }
          },
          {
            path: '/panoramic/board',
            name: 'board',
            component: () => import('@/views/panoramic/board/index.vue'),
            meta: {
              title: '板卡展示',
              isHide: false
            }
          }
        ]
      },
      // 状态管理
      {
        path: '/state ',
        name: 'state',
        component: () => import('@/layout/main.vue'),
        meta: {
          title: '状态管理',
          isHide: true,
          role: [0, 1, 2]
        },
        children: [
          {
            path: '/state/whole',
            name: 'stateWhole',
            component: () => import('@/views/state/whole/index.vue'),
            meta: {
              title: '整机适配状态',
              isHide: false
            }
          },
          {
            path: '/state/board',
            name: 'stateBoard',
            component: () => import('@/views/state/board/index.vue'),
            meta: {
              title: '板卡适配状态',
              isHide: false
            }
          }


        ]
      },
      // 计划管理
      {
        path: '/plan',
        name: 'plan',
        component: () => import('@/layout/main.vue'),
        redirect: '/plan/whole',
        meta: {
          title: '计划管理',
          isHide: false,
          role: [0, 1, 2]
        },
        children: [
          {
            path: '/plan/whole',
            name: 'planWhole',
            component: () => import('@/views/plan/whole/index.vue'),
            meta: {
              title: '整机计划',
              isHide: false
            }
          },
          {
            path: '/plan/board',
            name: 'planBoard',
            component: () => import('@/views/plan/board/index.vue'),
            meta: {
              title: '板卡计划',
              isHide: false
            }
          },
          {
            path: '/plan/version',
            name: 'planVersion',
            component: () => import('@/views/plan/version/index.vue'),
            meta: {
              title: '版本计划',
              isHide: false
            }
          },
          {
            path: '/plan/version/detail',
            name: 'planVersionDetail',
            component: () => import('@/views/plan/version/detail.vue'),
            meta: {
              title: '版本详情',
              isHide: true
            }
          },
          {
            path: '/plan/compatibility',
            name: 'sysCompatibility',
            component: () => import('@/views/compatibility/index.vue'),
            meta: {
              title: '发布统计',
              isHide: false,
              role: [0, 1, 2]
            }
          }
        ]
      },
      // 厂商管理
      {
        path: '/manufacturer',
        name: 'manufacturer',
        component: () => import('@/layout/main.vue'),
        redirect: '/manufacturer/cpu',
        meta: {
          title: '厂商管理',
          isHide: false,
          role: [0, 1]
        },
        children: [
          {
            path: '/manufacturer/cpu',
            name: 'manufacturerCpu',
            component: () => import('@/views/manufacturer/cpu/index.vue'),
            meta: {
              title: 'CPU厂商',
              isHide: false
            }
          },
          {
            path: '/manufacturer/whole',
            name: 'manufacturerWhole',
            component: () => import('@/views/manufacturer/whole/index.vue'),
            meta: {
              title: '整机厂商',
              isHide: false
            }
          },
          {
            path: '/manufacturer/chip',
            name: 'manufacturerChip',
            component: () => import('@/views/manufacturer/chip/index.vue'),
            meta: {
              title: '芯片厂商',
              isHide: false
            }
          },
          {
            path: '/manufacturer/board',
            name: 'manufacturerBoard',
            component: () => import('@/views/manufacturer/board/index.vue'),
            meta: {
              title: '板卡厂商',
              isHide: false
            }
          }
        ]
      },
      {
        path: '/sys',
        name: 'sys',
        component: () => import('@/layout/main.vue'),
        redirect: '/sys/user',
        meta: {
          title: '系统管理',
          isHide: false,
          role: [0]
        },
        children: [
          {
            path: '/sys/user',
            name: 'sysUser',
            component: () => import('@/views/sys/user/index.vue'),
            meta: {
              title: '用户管理',
              isHide: false
            }
          },
          {
            path: '/sys/log',
            name: 'sysLog',
            component: () => import('@/views/sys/log/index.vue'),
            meta: {
              title: '日志管理',
              isHide: false
            }
          }
        ]
      }
    ]
  }
];
