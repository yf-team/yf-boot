const config: {
  code_success: number | string // 成功响应码
  code_fail: number | string // 失败响应码
  code_overdure: number | string // 会话超时响应码
  default_headers: AxiosHeaders
  request_timeout: number
} = {
  /**
   * 接口成功返回状态码
   */
  code_success: 0,

  /**
   * 接口失败返回状态码
   */
  code_fail: 1,

  /**
   * 接口会话超时响应码
   */
  code_overdure: 10010002,

  /**
   * 接口请求超时时间
   */
  request_timeout: 60000,

  /**
   * 默认接口请求类型
   * 可选值：application/x-www-form-urlencoded multipart/form-data
   */
  default_headers: 'application/json'
}

export { config }
