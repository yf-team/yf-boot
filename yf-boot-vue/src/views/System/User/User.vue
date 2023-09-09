<template>
  <ContentWrap>
    <data-table
      :options="options"
      :query="query"
      @on-add="handleAdd(formRef)"
      @on-edit="handleEdit"
      ref="table"
    >
      <template #search>
        <DepartSelect v-model="query.params.deptCode" class="filter-item" />
        <el-input
          class="filter-item"
          v-model="query.params['roleName']"
          placeholder="搜索角色名称"
        />
      </template>

      <template #columns>
        <el-table-column type="selection" width="50px" />
        <el-table-column prop="userName" label="用户名" />
        <el-table-column prop="realName" label="姓名" />
        <el-table-column prop="deptCode_dictText" label="所属部门" />
        <el-table-column prop="roleNames" label="角色" />

        <el-table-column label="状态" width="180px" :align="'center'">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.state === 0">正常</el-tag>
            <el-tag type="danger" v-else>禁用</el-tag>
          </template>
        </el-table-column>
      </template>
    </data-table>

    <el-dialog v-model="dialogVisible" title="用户管理" width="50%" :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="登录账号" prop="userName">
              <el-input v-model="form.userName" autocomplete="off" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员姓名" prop="realName">
              <el-input v-model="form.realName" autocomplete="off" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptCode">
              <DepartSelect v-model="form.deptCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员角色" prop="roles">
              <RoleSelect v-model="form.roles" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="mobile">
              <el-input v-model="form.mobile" autocomplete="off" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="form.idCard" autocomplete="off" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户头像" prop="avatar">
              <el-input v-model="form.avatar" autocomplete="off" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave(formRef)">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { ContentWrap } from '@/components/ContentWrap'
import { DataTable } from '@/components/DataTable'
import { ref, reactive, unref } from 'vue'
import type { OptionsType, TableQueryType } from '@/components/DataTable/src/types'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { saveApi, detailApi } from '@/api/sys/user'
import RoleSelect from '../Role/components/RoleSelect.vue'
import DepartSelect from '../Depart/components/DepartSelect.vue'
import type { UserDataType } from './types'

// 表格查询参数
let query = ref<TableQueryType>({
  current: 1,
  size: 10,
  params: {
    title: '',
    deptCode: ''
  }
})

// 表格默认参数
let options = ref<OptionsType>({
  listUrl: '/api/sys/user/paging',
  delUrl: '/api/sys/user/delete',
  add: {
    enable: true,
    permission: ['sys:user:add']
  },
  edit: {
    enable: true,
    permission: ['sys:user:edit']
  },
  del: {
    enable: true,
    permission: ['sys:user:delete']
  },
  // 批量操作
  batch: [
    {
      key: 'state',
      label: '启用',
      params: { state: 0 },
      action: '/api/sys/user/state'
    },
    {
      key: 'state',
      label: '禁用',
      params: { state: 1 },
      action: '/api/sys/user/state'
    }
  ]
})

const table = ref()
const dialogVisible = ref(false)
const form = ref<UserDataType>({})
const formRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  roleName: [
    {
      required: true,
      message: '角色名称必须输入',
      trigger: 'blur'
    }
  ],
  dataScope: [
    {
      required: true,
      message: '数据权限必须选择',
      trigger: 'blur'
    }
  ],
  roleLevel: [
    {
      required: true,
      message: '角色级别不能为空',
      trigger: 'blur'
    }
  ]
})

const handleAdd = (formEl: FormInstance | undefined) => {
  dialogVisible.value = true
  form.value = {}
  formEl?.resetFields()
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleEdit = (row: any) => {
  detailApi({ id: row.id }).then((res) => {
    // 数据
    console.log('data', res.data)
    form.value = res.data
  })

  dialogVisible.value = true
}

const handleSave = (formEl: FormInstance | undefined) => {
  if (!formEl) return

  formEl.validate((valid) => {
    if (valid) {
      const formData = unref(form)
      saveApi(formData).then(() => {
        ElMessage({
          showClose: true,
          message: '操作成功！',
          type: 'success'
        })
        // 刷新表格
        table.value.reload()
        dialogVisible.value = false
      })
    } else {
      dialogVisible.value = false
    }
  })
}
</script>
