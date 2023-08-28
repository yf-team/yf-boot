import { useAxios } from '@/hooks/web/useAxios'

const request = useAxios()

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
