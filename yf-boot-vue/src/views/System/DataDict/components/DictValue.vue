<template>
  <div v-loading="loading" element-loading-text="加载中...">
    <el-table
      :data="records"
      row-key="id"
      stripe
      default-expand-all
      style="width: 100%"
      :tree-props="{
        children: 'children',
        hasChildren: 'hasChildren'
      }"
    >
      <el-table-column label="名称">
        <template #default="scope">
          <el-input v-model="scope.row.title" style="width: calc(100% - 150px)" />
        </template>
      </el-table-column>

      <el-table-column label="值" v-if="props.dictType === 1">
        <template #default="scope">
          <el-input v-model="scope.row.value" />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150px" :align="'center'">
        <template #default="scope">
          <el-button
            :disabled="!scope.row.id"
            type="default"
            size="small"
            circle
            icon="Plus"
            @click="handleAddSub(scope.row)"
          />
          <el-button type="primary" size="small" icon="Check" circle @click="saveItem(scope.row)" />
          <el-button
            :disabled="!scope.row.id"
            type="danger"
            size="small"
            icon="Delete"
            circle
            @click="removeItem(scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" style="width: 100%; margin-top: 20px" @click="handleAdd"
      >添加新的项目</el-button
    >
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref, watch } from 'vue'
import { subListApi, subSaveApi, subDeleteApi } from '@/api/sys/dict'
import { ElMessage } from 'element-plus'

// 页面参数
const records = ref()
const loading = ref(false)

// 表格参数
const props = defineProps({
  dicCode: {
    type: String,
    default: ''
  },
  dictType: {
    type: Number,
    default: 1
  }
})

// 监听数据变化
watch(
  () => props.dicCode,
  (val) => {
    if (!val) return
    loadData()
  }
)

// 加载数据
const loadData = () => {
  loading.value = true
  subListApi({ dicCode: props.dicCode })
    .then((res) => {
      records.value = res.data
      loading.value = false
    })
    .catch(() => {
      loading.value = false
    })
}

const handleAdd = () => {
  if (!records.value) {
    records.value = []
  }
  records.value.push({ parentId: 0, title: '', value: '', dicCode: props.dicCode })
}

const saveItem = (row: any) => {
  subSaveApi(row).then(() => {
    ElMessage({
      showClose: true,
      message: '添加成功！',
      type: 'success'
    })

    loadData()
  })
}

const removeItem = (row: any) => {
  subDeleteApi({ ids: row.id }).then(() => {
    ElMessage({
      showClose: true,
      message: '删除成功！',
      type: 'success'
    })

    loadData()
  })
}

const handleAddSub = (row: any) => {
  if (!row.children) {
    row.children = []
  }
  row.children.push({ title: '', value: '', parentId: row.id, dicCode: props.dicCode })
}

// 加载第一页数据
onMounted(() => {
  // 首次加载数据
  loadData()
})
</script>
