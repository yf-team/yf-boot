import request from '@/config/axios'

export const apiLogin = (data) => {
  return request.post({
    url: '/api/sys/user/login',
    data
  })
}

// 用户注册
export const apiRegister = (data) => {
  return request.post({
    url: '/api/sys/user/reg',
    data
  })
}

export const loginOutApi = () => {
  return request.post({ url: '/api/sys/user/logout' })
}

export const routesApi = (data: any) => {
  return request.post({
    url: '/api/sys/menu/routes',
    data
  })
}
