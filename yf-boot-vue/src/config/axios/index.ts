import { useCache } from '@/hooks/web/useCache'
import { useAppStoreWithOut } from '@/store/modules/app'
import { config } from '@/config/axios/config'

import router, { resetRouter } from '@/router'
import { useTagsViewStore } from '@/store/modules/tagsView'
import { ElMessage } from 'element-plus'

const tagsViewStore = useTagsViewStore()

import axios, {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosRequestHeaders,
  AxiosResponse,
  AxiosError
} from 'axios'

import qs from 'qs'
const { code_success, code_overdure } = config

// 接口请求地址
export const PATH_URL = import.meta.env.VITE_API_HOST

const { wsCache } = useCache()
const appStore = useAppStoreWithOut()

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: PATH_URL, // api 的 base_url
  timeout: config.request_timeout // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const userInfo = appStore.getUserInfo

    // 传入token
    if (userInfo && userInfo.token) {
      if (config && config.headers) {
        config.headers['token'] = userInfo.token
      } else {
        config.headers = { token: userInfo.token }
      }
    }

    if (
      config.method === 'post' &&
      (config.headers as AxiosRequestHeaders)['Content-Type'] ===
        'application/x-www-form-urlencoded'
    ) {
      config.data = qs.stringify(config.data)
    }

    // get参数编码
    if (config.method === 'get' && config.params) {
      let url = config.url as string
      url += '?'
      const keys = Object.keys(config.params)
      for (const key of keys) {
        if (config.params[key] !== void 0 && config.params[key] !== null) {
          url += `${key}=${encodeURIComponent(config.params[key])}&`
        }
      }
      url = url.substring(0, url.length - 1)
      config.params = {}
      config.url = url
    }
    return config
  },
  (error: AxiosError) => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  (response: AxiosResponse<Recordable>) => {
    if (response.data.code === code_success) {
      return response.data
    }

    console.log(response.data.code)

    if (response.data.code === code_overdure) {
      ElMessage.error(response.data.msg)

      // 置空残留会话
      appStore.setUserInfo({})
      // 清除缓存数据等
      wsCache.clear()
      // 重置静态路由表
      tagsViewStore.delAllViews()
      resetRouter()

      router.replace('/login')
      return Promise.reject(response.data)
    } else {
      ElMessage.error(response.data.msg)
    }
  },
  (error: AxiosError) => {
    console.log('err' + error) // for debug
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

export { service }
