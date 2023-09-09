// 按钮对应的类型
export type ButtonType = {
  enable: boolean
  permission?: string[]
  router?: string
  action?: string
}

// 表格列属性
export type TableQueryType = {
  current: number
  size: number
  params: any
}

// 批量操作
export type BatchType = {
  key: string
  label: string
  params?: any
  action?: string // 操作请求提交URL
  idsKey?: string // 数据ids的JSON名称
}

// 完整的表格Options参数
export type OptionsType = {
  listUrl: string // 分页接口
  delUrl?: string // 删除接口
  rowKey?: string
  ip?: ButtonType // 导入
  op?: ButtonType // 导出
  add?: ButtonType
  edit?: ButtonType
  del?: ButtonType
  batch?: BatchType[]
}
