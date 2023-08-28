import { config } from '@/config/axios/config'
import { MockMethod } from 'vite-plugin-mock'

const { result_code } = config

const timeout = 1000

const adminList = [
  {
    path: '/level',
    component: '#',
    redirect: '/level/menu1/menu1-1/menu1-1-1',
    name: 'Level',
    meta: {
      title: 'router.level',
      icon: 'carbon:skill-level-advanced'
    },
    children: [
      {
        path: 'menu1',
        name: 'Menu1',
        component: '##',
        redirect: '/level/menu1/menu1-1/menu1-1-1',
        meta: {
          title: 'router.menu1'
        },
        children: [
          {
            path: 'menu1-1',
            name: 'Menu11',
            component: '##',
            redirect: '/level/menu1/menu1-1/menu1-1-1',
            meta: {
              title: 'router.menu11',
              alwaysShow: true
            },
            children: [
              {
                path: 'menu1-1-1',
                name: 'Menu111',
                component: 'views/Level/Menu111',
                meta: {
                  title: 'router.menu111'
                }
              }
            ]
          },
          {
            path: 'menu1-2',
            name: 'Menu12',
            component: 'views/Level/Menu12',
            meta: {
              title: 'router.menu12'
            }
          }
        ]
      },
      {
        path: 'menu2',
        name: 'Menu2Demo',
        component: 'views/Level/Menu2',
        meta: {
          title: 'router.menu2'
        }
      }
    ]
  },
  // 系统菜单
  {
    path: '/system',
    component: '#',
    redirect: '/system/role',
    name: 'System',
    meta: {
      title: '系统设置',
      icon: 'carbon:settings',
      alwaysShow: true
    },
    children: [
      {
        path: 'role',
        name: 'Role',
        component: 'views/System/Role/Role',
        meta: {
          title: '角色管理'
        }
      }
    ]
  }
]

const testList: string[] = [
  '/level',
  '/level/menu1',
  '/level/menu1/menu1-1',
  '/level/menu1/menu1-1/menu1-1-1',
  '/level/menu1/menu1-2',
  '/level/menu2'
]

export default [
  // 列表接口
  {
    url: '/role/list',
    method: 'get',
    timeout,
    response: ({ query }) => {
      const { roleName } = query
      return {
        code: result_code,
        data: {
          list: roleName === 'admin' ? adminList : testList
        }
      }
    }
  },

  // 列表接口
  {
    url: '/role/paging',
    method: 'post',
    timeout,
    response: () => {
      return {
        code: result_code,
        data: {
          current: 1,
          hitCount: false,
          optimizeCountSql: true,
          orders: [],
          pages: 1,
          records: [
            {
              createBy: '10001',
              createTime: '2022-07-13 01:37:07',
              dataFlag: 0,
              dataScope: 4,
              dataScope_dictText: '全部数据',
              id: 'abc',
              remark: '',
              roleLevel: 29,
              roleName: 'abc',
              roleType: 2,
              roleType_dictText: '管理员/教师/后端用户',
              updateBy: '',
              updateTime: '2022-07-13 01:37:06'
            },
            {
              createBy: '10001',
              createTime: '2022-06-24 17:17:51',
              dataFlag: 0,
              dataScope: 2,
              dataScope_dictText: '本部门数据',
              id: 'depart',
              remark: '',
              roleLevel: 980,
              roleName: '本部门及以下',
              roleType: 2,
              roleType_dictText: '管理员/教师/后端用户',
              updateBy: '10001',
              updateTime: '2022-06-28 11:02:22'
            },
            {
              createBy: '',
              createTime: '2020-12-03 16:52:16',
              dataFlag: 1,
              dataScope: 4,
              dataScope_dictText: '全部数据',
              id: 'sa',
              remark: '',
              roleLevel: 999,
              roleName: '超级管理员',
              roleType: 2,
              roleType_dictText: '管理员/教师/后端用户',
              updateBy: '10001',
              updateTime: '2021-05-31 18:59:24'
            }
          ],
          searchCount: true,
          size: 10,
          total: 300
        }
      }
    }
  }
] as MockMethod[]
