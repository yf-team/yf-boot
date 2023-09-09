<template>
  <ContentWrap>
    <data-table
      :options="options"
      :query="query"
      @on-add="handleAdd(formRef)"
      @on-edit="handleEdit"
      ref="tableRef"
    >
      <template #search>
        <el-input
          class="filter-item"
          clearable
          v-model="query.params['roleName']"
          placeholder="搜索角色名称"
        />
      </template>

      <template #columns>
        <el-table-column type="selection" width="50px" />

        <el-table-column prop="roleName" label="角色名称" />

        <el-table-column prop="roleLevel" label="角色级别" />

        <el-table-column prop="dataScope_dictText" label="数据权限" />

        <el-table-column label="操作" width="180px" :align="'center'">
          <template #default="scope">
            <el-button icon="Setting" type="primary" size="small" @click="showGrant(scope.row)"
              >角色授权</el-button
            >
          </template>
        </el-table-column>
      </template>
    </data-table>

    <el-dialog v-model="dialogVisible" title="角色管理" width="30%" :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" autocomplete="off" />
        </el-form-item>

        <el-form-item label="数据权限" prop="dataScope">
          <DictListSelect dic-code="data_scope" v-model="form.dataScope" />
        </el-form-item>

        <el-form-item label="角色级别" prop="roleLevel">
          <el-input-number v-model="form.roleLevel" autocomplete="off" :min="0" :max="999" />
          <div><small>数字越大级别越大，数字小的角色不能修改数字高角色的数据</small></div>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave(formRef)">保存</el-button>
        </span>
      </template>
    </el-dialog>

    <Grant :role-id="grantRoleId" v-model="grantVisible" />
  </ContentWrap>
</template>

<script lang="ts" setup>
import { ContentWrap } from '@/components/ContentWrap'
import { DataTable } from '@/components/DataTable'
import { ref, reactive, unref } from 'vue'
import type { OptionsType, TableQueryType } from '@/components/DataTable/src/types'
import type { FormInstance, FormRules } from 'element-plus'
import DictListSelect from '@/components/DictListSelect/src/DictListSelect.vue'
import type { RoleDataType } from './types'
import { ElMessage } from 'element-plus'
import { saveApi } from '@/api/sys/role'
import Grant from './components/Grant.vue'

// 表格查询参数
let query = ref<TableQueryType>({
  current: 1,
  size: 10,
  params: {
    title: ''
  }
})

// 表格默认参数
let options = ref<OptionsType>({
  listUrl: '/api/sys/role/paging',
  delUrl: '/api/sys/role/delete',
  add: {
    enable: true,
    permission: ['role:add']
  },
  edit: {
    enable: true,
    permission: ['role:edit']
  },
  del: {
    enable: true,
    permission: ['role:delete']
  },

  // 批量操作
  batch: [
    {
      key: 'state',
      label: '启用',
      params: { state: 1 },
      action: '/api/user/state',
      idsKey: 'userIds'
    },
    {
      key: 'state',
      label: '禁用',
      params: { state: 0 }
    }
  ]
})

const tableRef = ref()

const dialogVisible = ref(false)
const form = ref<RoleDataType>({})
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
  dialogVisible.value = true
  form.value = row
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
        tableRef.value.reload()
        dialogVisible.value = false
      })
    } else {
      dialogVisible.value = false
    }
  })
}

const grantVisible = ref(false)
const grantRoleId = ref('')

// 获取角色信息
const showGrant = (row: any) => {
  grantVisible.value = true
  grantRoleId.value = row.id
}
</script>
