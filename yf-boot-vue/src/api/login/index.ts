import request from '@/config/axios'

export const loginApi = (data) => {
  return request.post({
    url: '/api/sys/user/login',
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
