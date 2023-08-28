<template>
  <el-tree-select
    style="width: 100%"
    :check-strictly="true"
    v-model="value"
    :data="treeData"
    :render-after-expand="false"
    @change="handlerChange"
    :props="{ label: 'deptName' }"
  />
</template>

<script lang="ts" setup>
import { ref, watch, onMounted, unref } from 'vue'
import { treeApi } from '@/api/sys/depart'

const value = ref()
const treeData = ref([])

// 组件参数
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
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
  await treeApi().then((res) => {
    treeData.value = res.data
  })
}

// 加载数据
const handlerChange = async () => {
  emit('update:modelValue', unref(value))
}

// 加载第一页数据
onMounted(() => {
  // 首次加载数据
  loadData()
})
</script>
