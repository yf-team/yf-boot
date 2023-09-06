import request from '@/config/axios'

export const apiDetail = () => {
  return request.post({ url: '/api/sys/config/switch/detail', data: {} })
}

export const apiSave = (data?: any) => {
  return request.post({
    url: '/api/sys/config/switch/save',
    data
  })
}
