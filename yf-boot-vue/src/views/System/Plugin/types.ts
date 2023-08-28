// 实体对象
export type RoleDataType = {
  id?: string
  roleName?: string
  dataScope?: string
  roleLevel?: string
}

// 用户对象
export type UserDataType = {
  id?: string
  userName?: string
  realName?: string
  deptCode?: string
  roles?: string[]
  mobile?: string
  idCard?: string
  avatar?: string
}
