import request from '@/config/axios'

export const pagingApi = (data?: any) => {
  return request.post({
    url: '/api/sys/role/paging',
    data
  })
}

export const saveApi = (data: any) => {
  return request.post({
    url: '/api/sys/role/save',
    data
  })
}

export const listMenuApi = (data: any) => {
  return request.post({
    url: '/api/sys/role/list-menus',
    data
  })
}

export const saveMenuApi = (data: any) => {
  return request.post({
    url: '/api/sys/role/save-menus',
    data
  })
}
