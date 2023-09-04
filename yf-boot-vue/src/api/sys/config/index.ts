import request from '@/config/axios'

export const detailApi = (data?: any) => {
  return request.post({ url: '/api/sys/config/detail', data })
}

export const saveApi = (data?: any) => {
  return request.post({
    url: '/api/sys/config/save',
    data
  })
}
