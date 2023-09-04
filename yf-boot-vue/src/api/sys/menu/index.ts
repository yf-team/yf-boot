import request from '@/config/axios'

export const treeApi = () => {
  return request.post({
    url: '/api/sys/menu/tree'
  })
}

export const saveApi = (data: any) => {
  return request.post({
    url: '/api/sys/menu/save',
    data
  })
}

export const deleteApi = (data: any) => {
  return request.post({
    url: '/api/sys/menu/delete',
    data
  })
}

export const detailApi = (data: any) => {
  return request.post({
    url: '/api/sys/menu/detail',
    data
  })
}

export const sortApi = (data: any) => {
  return request.post({
    url: '/api/sys/menu/sort',
    data
  })
}

// export const subListApi = (data: any) => {
//   return request.post({
//     url: '/api/sys/dic/value/tree',
//     data
//   })
// }

// export const subSaveApi = (data: any) => {
//   return request.post({
//     url: '/api/sys/dic/value/save',
//     data
//   })
// }

// export const subDeleteApi = (data: any) => {
//   return request.post({
//     url: '/api/sys/dic/value/delete',
//     data
//   })
// }
