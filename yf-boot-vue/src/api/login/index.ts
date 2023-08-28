import { useAxios } from '@/hooks/web/useAxios'
import type { UserLoginType, UserType } from './types'

const request = useAxios()

export const loginApi = (data: UserLoginType) => {
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

export const getUserListApi = ({ params }: AxiosConfig) => {
  return request.get<{
    total: number
    list: UserType[]
  }>({ url: '/user/list', params })
}

export const getAdminRoleApi = ({ params }) => {
  return request.get<{
    list: AppCustomRouteRecordRaw[]
  }>({ url: '/role/list', params })
}

export const getTestRoleApi = ({ params }) => {
  return request.get<{
    list: string[]
  }>({ url: '/role/list', params })
}
