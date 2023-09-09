<template>
  <el-select
    filterable
    remote
    clearable
    remote-show-suffix
    v-model="value"
    :remote-method="handlerSearch"
    @change="handleChange"
    @clear="handleClear"
    :loading="loading"
    class="filter-item"
  >
    <el-option v-for="item in listData" :key="item.value" :label="item.title" :value="item.value" />
  </el-select>
</template>

<script lang="ts" setup>
import { onMounted, ref, watch } from 'vue'
import { subListApi } from '@/api/sys/dict'
import type { DictValueType } from './types'

// 列表数据
const listData = ref<DictValueType[]>([])

// 表格参数
const props = defineProps({
  modelValue: {
    type: String
  },
  dicCode: {
    type: String,
    default: ''
  }
})

// 选定值
const value = ref(props.modelValue)
const loading = ref(false)

// 监听数据变化
watch(
  () => props.modelValue,
  (val) => {
    if (val) {
      value.value = val.toString()
    } else {
      value.value = ''
    }
  }
)

watch(
  () => props.dicCode,
  (val) => {
    if (!val) return
    loadData()
  }
)

// 事件定义
const emit = defineEmits(['update:modelValue', 'onSelect', 'onClear'])

// 选择数据
const handleChange = () => {
  emit('onSelect', value.value)
  emit('update:modelValue', value.value)
}

// 清理数据
const handleClear = () => {
  emit('update:modelValue', '')
  emit('onClear')
}

// 加载数据
const loadData = (query?: String) => {
  loading.value = true
  subListApi({ dicCode: props.dicCode, title: query }).then((res) => {
    listData.value = res.data
    loading.value = false
  })
}

// 远程搜索
const handlerSearch = (query: string) => {
  if (query) {
    loadData(query)
  }
}

// 加载第一页数据
onMounted(() => {
  // 赋值
  if (props.modelValue) {
    value.value = props.modelValue.toString()
  } else {
    value.value = ''
  }

  // 首次加载数据
  loadData('')
})
</script>

<style scoped></style>
