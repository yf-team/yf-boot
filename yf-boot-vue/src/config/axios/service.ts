import axios from 'axios'
import config from './config'

import { AxiosInstance, InternalAxiosRequestConfig, RequestConfig, AxiosResponse } from './types'
import { ElMessage } from 'element-plus'
import { useStorage } from '@/hooks/web/useStorage'
import { useTagsViewStore } from '@/store/modules/tagsView'
import router, { resetRouter } from '@/router'
import { useUserStoreWithOut } from '@/store/modules/user'
const { clear } = useStorage()
const userStore = useUserStoreWithOut()
const tagsViewStore = useTagsViewStore()

export const PATH_URL = import.meta.env.VITE_API_HOST || ''
const code_success = 0
const code_overdue = 10010002
const abortControllerMap: Map<string, AbortController> = new Map()
const axiosInstance: AxiosInstance = axios.create({
  ...config,
  baseURL: PATH_URL
})

axiosInstance.interceptors.request.use((res: InternalAxiosRequestConfig) => {
  const controller = new AbortController()
  const url = res.url || ''
  const userInfo = userStore.getUserInfo
  // 传入token
  if (userInfo && userInfo.token && res && res.headers) {
    res.headers['token'] = userInfo.token
  }
  res.signal = controller.signal
  abortControllerMap.set(url, controller)
  return res
})

axiosInstance.interceptors.response.use(
  (res: AxiosResponse) => {
    console.log('响应结果', res)

    if (res.data.code === code_success) {
      return res.data
    }

    if (res.data.code === code_overdue) {
      ElMessage.error(res.data.msg)
      // 置空残留会话
      userStore.setUserInfo({})
      // 清除缓存数据等
      clear()
      // 重置静态路由表
      tagsViewStore.delAllViews()
      // 重置静态路由表
      resetRouter()
      // 跳转到登录页面
      router.replace('/login')
    } else {
      ElMessage.error(res.data.msg)
    }

    const url = res.config.url || ''
    abortControllerMap.delete(url)
    return res.data
  },
  (err: any) => {
    ElMessage.error('糟糕，服务器开小差了！' + err)
  }
)

const service = {
  request: (config: RequestConfig) => {
    return new Promise((resolve, reject) => {
      if (config.interceptors?.requestInterceptors) {
        config = config.interceptors.requestInterceptors(config as any)
      }

      axiosInstance
        .request(config)
        .then((res) => {
          resolve(res)
        })
        .catch((err: any) => {
          reject(err)
        })
    })
  },
  cancelRequest: (url: string | string[]) => {
    const urlList = Array.isArray(url) ? url : [url]
    for (const _url of urlList) {
      abortControllerMap.get(_url)?.abort()
      abortControllerMap.delete(_url)
    }
  },
  cancelAllRequest() {
    for (const [_, controller] of abortControllerMap) {
      controller.abort()
    }
    abortControllerMap.clear()
  }
}

export default service
