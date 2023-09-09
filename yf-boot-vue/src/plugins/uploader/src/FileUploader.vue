<template>
  <el-upload
    ref="uploadRef"
    :action="action"
    :headers="headers"
    :limit="1"
    :file-list="fileList"
    :on-exceed="handleExceed"
    :on-success="handleSuccess"
    list-type="picture-card"
    :auto-upload="true"
  >
    <el-icon>
      <Plus />
    </el-icon>
    <template #file="{ file }">
      <div style="background: #b3b3b3">
        <img
          class="el-upload-list__item-thumbnail"
          v-if="file && file.url"
          :src="file.url"
          alt=""
        />
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <el-icon><zoom-in /></el-icon>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
            <el-icon><Delete /></el-icon>
          </span>
        </span>
      </div>
    </template>
  </el-upload>

  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue'
import { v4 as uuidv4 } from 'uuid'

import type { UploadFile, UploadInstance, UploadUserFile, UploadRawFile } from 'element-plus'

const uploadRef = ref<UploadInstance>()
import { useUserStoreWithOut } from '@/store/modules/user'

// 已上传列表
const fileList = ref<UploadUserFile[]>([])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)
const action = import.meta.env.VITE_API_HOST + '/api/common/file/upload'

const userInfo = useUserStoreWithOut().getUserInfo
const headers = { token: userInfo.token }

// 事件定义
const emit = defineEmits(['update:modelValue'])

// 参数
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

watch(
  () => props.modelValue,
  (newVal) => {
    fileList.value = [{ url: newVal, name: newVal }]
  }
)

const handleExceed = (files: File[]) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = uuidv4()
  uploadRef.value!.handleStart(file)
  uploadRef.value!.submit()
}

// 移除文件
const handleRemove = (file: UploadFile) => {
  console.log(file)
  fileList.value = []
}

// 直接返回服务器URL
const handleSuccess = (response: any) => {
  console.log('上传成功：', response.data.url)
  emit('update:modelValue', response.data.url)
}

const handlePictureCardPreview = (file: UploadFile) => {
  dialogImageUrl.value = file.url!
  dialogVisible.value = true
}
</script>

<style scoped>
:deep(.el-upload-list--picture-card) {
  --el-upload-list-picture-card-size: 80px;
  display: inline-flex;
  flex-wrap: wrap;
  margin: 0;
}

:deep(.el-upload--picture-card) {
  --el-upload-picture-card-size: 80px;
}

:deep(.el-upload-list--picture-card .el-upload-list__item-actions) {
  font-size: 16px;
}
</style>
