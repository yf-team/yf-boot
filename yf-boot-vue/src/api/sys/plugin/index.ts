import request from '@/config/axios'

export const detailApi = (data?: any) => {
  return request.post({
    url: '/api/sys/config/detail',
    data
  })
}
