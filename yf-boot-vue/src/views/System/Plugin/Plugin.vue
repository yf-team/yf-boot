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
        <el-table-column prop="title" label="插件名称" />
        <el-table-column prop="groupId" label="插件类型" />
        <el-table-column prop="inUse" label="使用状态" />

        <el-table-column label="状态" width="180px" :align="'center'">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.state === 0">正常</el-tag>
            <el-tag type="danger" v-else>禁用</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作项" width="180px" :align="'center'">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              icon="Setting"
              @click="showConfig(scope.row)"
              circle
            />
          </template>
        </el-table-column>
      </template>
    </data-table>

    <el-dialog v-model="dialogVisible" title="配置插件" width="40%" :before-close="handleClose">
      <el-form :model="form" ref="formRef" label-width="150px">
        <template v-for="item in configItems" :key="item.name">
          <el-form-item :label="item.title" :prop="item.name">
            <el-input v-model="form[item.name]" autocomplete="off" />
          </el-form-item>
        </template>
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
import { detailApi as schemaDetail } from '@/api/sys/plugin/schema'

// 表格查询参数
let query = ref<TableQueryType>({
  current: 1,
  size: 10,
  params: {
    title: '',
    deptCode: ''
  }
})

const configItems = ref([])

// 表格默认参数
let options = ref<OptionsType>({
  listUrl: '/api/sys/plugin/data/paging',
  delUrl: '/api/sys/plugin/data/delete',
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
  batchs: [
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
const form = ref({})
const formRef = ref<FormInstance>()

const showConfig = (data: any) => {
  // 加载元数据
  schemaDetail({ id: data.schemaId }).then((res) => {
    configItems.value = JSON.parse(res.data.schemaData)
    console.log(configItems.value)
  })

  if (data.configData) {
    form.value = JSON.parse(data.configData)
  } else {
    form.value = {}
  }

  dialogVisible.value = true
}

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
