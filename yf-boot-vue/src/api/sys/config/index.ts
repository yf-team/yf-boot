import { useAxios } from '@/hooks/web/useAxios'

const request = useAxios()

export const detailApi = (data?: any) => {
  return request.post({
    url: '/api/sys/config/detail',
    data
  })
}
