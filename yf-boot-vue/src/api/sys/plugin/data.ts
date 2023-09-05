import request from '@/config/axios'

export const saveConfig = (data?: any) => {
  return request.post({
    url: '/api/sys/plugin/data/save',
    data
  })
}
