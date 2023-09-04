import request from '@/config/axios'

export const pagingApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/paging',
    data
  })
}

export const saveApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/save',
    data
  })
}

export const detailApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/detail',
    data
  })
}

export const subListApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/value/tree',
    data
  })
}

export const subSaveApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/value/save',
    data
  })
}

export const subDeleteApi = (data: any) => {
  return request.post({
    url: '/api/sys/dic/value/delete',
    data
  })
}
