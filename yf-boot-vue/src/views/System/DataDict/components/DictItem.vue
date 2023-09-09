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
        <DictListSelect v-model="query.params.type" dic-code="dic_type" class="filter-item" />
        <el-input v-model="query.params.title" placeholder="搜名称或编码" class="filter-item" />
      </template>

      <template #columns>
        <el-table-column type="selection" width="50px" />

        <el-table-column prop="title" label="名称" sortable />

        <el-table-column prop="type_dictText" label="类型" sortable />

        <el-table-column prop="code" label="编码" sortable />

        <el-table-column prop="remark" label="备注信息" />

        <el-table-column label="操作" width="180px" :align="'center'">
          <template #default="scope">
            <el-button type="primary" size="small" @click="showItem(scope.row)">字典项</el-button>
          </template>
        </el-table-column>
      </template>
    </data-table>

    <el-dialog v-model="dialogVisible" title="保存数据字典" width="30%" :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="类型" prop="type">
          <DictListSelect v-model="form.type" dic-code="dic_type" />
        </el-form-item>

        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title" autocomplete="off" />
        </el-form-item>

        <el-form-item label="编码" prop="code">
          <el-input v-model="form.code" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" autocomplete="off" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave(formRef)">保存</el-button>
        </span>
      </template>
    </el-dialog>

    <el-drawer
      v-model="drawerVisible"
      title="配置字典项"
      direction="rtl"
      :close-on-click-modal="false"
      size="50%"
      :before-close="handleClose"
    >
      <DictValue :dic-code="dicCode" :dict-type="dictType" />
    </el-drawer>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { ContentWrap } from '@/components/ContentWrap'
import { DataTable } from '@/components/DataTable'
import { ref, unref, reactive } from 'vue'
import type { OptionsType, TableQueryType } from '@/components/DataTable/src/types'
import type { FormInstance, FormRules } from 'element-plus'
import { saveApi } from '@/api/sys/dict'
import DictValue from './DictValue.vue'
import DictListSelect from '@/components/DictListSelect/src/DictListSelect.vue'
import { ElMessage } from 'element-plus'
import type { DictDataType } from './types'
// 表格查询参数
let query = ref<TableQueryType>({
  current: 1,
  size: 10,
  params: {}
})

// 添加窗口
let dialogVisible = ref(false)

// 表单对象
const form = ref<DictDataType>({})

const checkCode = (_rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('编码必须填写！'))
  }

  // 必须调用的
  callback()
}

const formRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  type: [
    {
      required: true,
      message: '类型必须选择！',
      trigger: 'blur'
    }
  ],
  title: [
    {
      required: true,
      message: '请输入字典名称！',
      trigger: 'blur'
    }
  ],
  code: [
    {
      required: true,
      validator: checkCode,
      trigger: 'blur'
    }
  ]
})

// 表格默认参数
let options = ref<OptionsType>({
  listUrl: '/api/sys/dic/paging',
  delUrl: '/api/sys/dic/delete',
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
    permission: ['']
  }
})

const tableRef = ref()

const handleAdd = (formEl: FormInstance | undefined) => {
  form.value = {}
  dialogVisible.value = true
  formEl?.resetFields()
}

const handleClose = () => {
  dialogVisible.value = false
  drawerVisible.value = false
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

        console.log('关闭?')
        dialogVisible.value = false
        // 刷新表格
        tableRef.value.reload()
      })
    } else {
      dialogVisible.value = false
    }
  })
}

const drawerVisible = ref(false)
const dicCode = ref('')
const dictType = ref(0)

const showItem = (row: any) => {
  dicCode.value = row.code
  dictType.value = row.type
  drawerVisible.value = true
}
</script>
