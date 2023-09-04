import request from '@/config/axios'

export const saveApi = (data: any) => {
  return request.post({
    url: '/api/sys/user/save',
    data
  })
}

export const detailApi = (data: any) => {
  return request.post({
    url: '/api/sys/user/detail',
    data
  })
}
