<template>
  <el-select
    v-model="value"
    multiple
    :placeholder="props.title"
    @change="selectChange"
    style="width: 100%"
  >
    <el-option v-for="item in options" :key="item.id" :label="item.roleName" :value="item.id" />
  </el-select>
</template>

<script lang="ts" setup>
import { PropType, ref, watch, onMounted, unref } from 'vue'
import { pagingApi } from '@/api/sys/role'
import type { RoleDataType } from '../types'

const value = ref<String[]>([])
const options = ref<RoleDataType[]>([])

// 组件参数
const props = defineProps({
  modelValue: {
    type: Array as PropType<string[]>,
    default: () => []
  },
  title: {
    type: String,
    default: '请选择数据'
  }
})

const emit = defineEmits(['update:modelValue'])

// 监听数据变化
watch(
  () => props.modelValue,
  (val) => {
    value.value = val
  }
)

// 加载数据
const loadData = async () => {
  // 加载下拉列表
  await pagingApi({ current: 1, size: 100, params: {} }).then((res) => {
    options.value = res.data.records
  })
}

// 选定内容
const selectChange = () => {
  emit('update:modelValue', unref(value))
}

// 加载第一页数据
onMounted(() => {
  // 首次加载数据
  loadData()
})
</script>
