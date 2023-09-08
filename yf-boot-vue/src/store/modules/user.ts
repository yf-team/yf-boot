import { defineStore } from 'pinia'
import { store } from '../index'
import { apiRegister, routesApi, apiLogin } from '@/api/login'
import { UserLoginType } from '@/api/login/types'
import { useStorage } from '@/hooks/web/useStorage'
import { usePermissionStore } from '@/store/modules/permission'
import { RouteRecordRaw } from 'vue-router'
import router from '@/router'
const { getStorage, setStorage, removeStorage } = useStorage()

const permissionStore = usePermissionStore()

export interface UserState {
  userInfo: UserInfoTypes
}

export const useUserStore = defineStore('userInfo', {
  state: (): UserState => ({
    userInfo: getStorage('userInfo')
  }),
  getters: {
    getUserInfo(): UserInfoTypes {
      return this.userInfo
    }
  },
  actions: {
    // 用户登录
    login(data?: UserLoginType): Promise<unknown> {
      return new Promise<void>((resolve, reject) => {
        // 注册用户
        apiLogin(data)
          .then(async (res) => {
            await this.setUserInfo(res.data)
            await this.generateRoutes()
            resolve(res.data)
          })
          .catch((err) => {
            reject(err)
          })
      })
    },

    // 用户注册
    register(data?: UserLoginType): Promise<unknown> {
      return new Promise<void>((resolve, reject) => {
        // 注册用户
        apiRegister(data)
          .then(async (res) => {
            await this.setUserInfo(res.data)
            await this.generateRoutes()
            resolve(res.data)
          })
          .catch((err) => {
            reject(err)
          })
      })
    },
    // 保存用户状态
    setUserInfo(data?: UserInfoTypes) {
      if (data && data.token) {
        this.userInfo = data
        setStorage('userInfo', this.userInfo)
      } else {
        this.userInfo = {}
        removeStorage('userInfo')
      }
    },

    // 构建路由并跳转首页
    async generateRoutes() {
      const res = await routesApi({})
      if (res) {
        const routers = res.data || []
        setStorage('roleRouters', routers)

        // 固定使用服务端方式
        await permissionStore.generateRoutes(routers).catch(() => {})

        permissionStore.getAddRouters.forEach((route) => {
          // 动态添加可访问路由表
          router.addRoute(route as RouteRecordRaw)
        })
        permissionStore.setIsAddRouters(true)
      }
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
