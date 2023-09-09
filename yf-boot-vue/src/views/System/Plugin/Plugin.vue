<template>
  <ContentWrap>
    <data-table :options="options" :query="query" ref="table">
      <template #search>
        <DictListSelect
          v-model="query.params.groupId"
          dic-code="plugin_group"
          class="filter-item"
        />

        <el-input class="filter-item" v-model="query.params.title" placeholder="插件名称" />
      </template>

      <template #columns>
        <el-table-column type="selection" width="50px" />
        <el-table-column prop="code" label="插件编号" />
        <el-table-column prop="title" label="插件名称" />
        <el-table-column prop="groupId_dictText" label="插件类型" />

        <el-table-column label="使用状态" width="180px" :align="'center'">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.inUse">使用中</el-tag>
            <el-tag type="danger" v-else>未使用</el-tag>
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

    <ElDialog v-model="dialogVisible" title="配置插件" width="40%" :before-close="handleClose">
      <el-form :model="form" ref="formRef" label-width="150px">
        <template v-for="item in configItems" :key="item.name">
          <el-form-item :label="item.title" :prop="item.name">
            <el-input v-model="form[item.name || 'none']" autocomplete="off" />
          </el-form-item>
        </template>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave(formRef)">保存</el-button>
        </span>
      </template>
    </ElDialog>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { ContentWrap } from '@/components/ContentWrap'
import { DataTable } from '@/components/DataTable'
import { ref, unref } from 'vue'
import type { OptionsType, TableQueryType } from '@/components/DataTable/src/types'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import { saveConfig } from '@/api/sys/plugin/data'
import { detailApi as schemaDetail } from '@/api/sys/plugin/schema'
import DictListSelect from '@/components/DictListSelect/src/DictListSelect.vue'
import type { ConfigIem } from './types'

// 表格查询参数
let query = ref<TableQueryType>({
  current: 1,
  size: 10,
  params: {
    title: '',
    deptCode: ''
  }
})

const configItems = ref<ConfigIem[]>()

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
const form = ref({})
const formRef = ref<FormInstance>()
const current = ref()

const showConfig = (data: any) => {
  // 当前数据
  current.value = data

  // 加载元数据
  schemaDetail({ id: data.schemaId }).then((res) => {
    configItems.value = JSON.parse(res.data.schemaData)
    console.log(configItems.value)
  })

  if (data.configData) {
    form.value = data.configData
  } else {
    form.value = {}
  }
  dialogVisible.value = true
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleSave = (formEl: FormInstance | undefined) => {
  if (!formEl) return

  formEl.validate((valid) => {
    if (valid) {
      const params = {
        id: current.value.id,
        configData: unref(form)
      }

      saveConfig(params).then(() => {
        ElMessage({
          showClose: true,
          message: '配置保存成功！',
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
