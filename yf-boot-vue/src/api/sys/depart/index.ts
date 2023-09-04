import request from '@/config/axios'

export const saveApi = (data: any) => {
  return request.post({
    url: '/api/sys/depart/save',
    data
  })
}

export const treeApi = (data?: any) => {
  return request.post({
    url: '/api/sys/depart/tree',
    data
  })
}
