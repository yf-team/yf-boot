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
      <div>
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <el-icon><zoom-in /></el-icon>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
            <el-icon><Download /></el-icon>
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
import { nextTick, ref, watch } from 'vue'
import { Delete, Download, Plus, ZoomIn } from '@element-plus/icons-vue'

import type {
  UploadFile,
  UploadFiles,
  UploadInstance,
  UploadUserFile,
  UploadRawFile
} from 'element-plus'

const uploadRef = ref<UploadInstance>()

import { useAppStoreWithOut } from '@/store/modules/app'

// 已上传列表
const fileList = ref<UploadUserFile[]>([])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)
const action = import.meta.env.VITE_API_HOST + '/api/common/file/upload'

const userInfo = useAppStoreWithOut().getUserInfo
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

const handleExceed = (files: File[], uploadFiles: UploadUserFile[]) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = new Date().getTime()
  console.log('uid', file.uid)
  uploadRef.value!.handleStart(file)
  uploadRef.value!.submit()
}

const handleFileChange = (uploadFile: UploadFile, uploadFiles: UploadFiles) => {
  if (uploadFiles.length > 1) {
    fileList.value = [uploadFiles[uploadFiles.length - 1]]
  }
}

const handleRemove = (file: UploadFile) => {
  console.log(file)
}

// 直接返回服务器URL
const handleSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
  console.log('上传成功：', response.data.url)
  emit('update:modelValue', response.data.url)
}

const handlePictureCardPreview = (file: UploadFile) => {
  dialogImageUrl.value = file.url!
  dialogVisible.value = true
}

const handleDownload = (file: UploadFile) => {
  console.log(file)
}
</script>

<style scoped>
/deep/ .el-upload-list--picture-card {
  --el-upload-list-picture-card-size: 80px;
  display: inline-flex;
  flex-wrap: wrap;
  margin: 0;
}

/deep/ .el-upload--picture-card {
  --el-upload-picture-card-size: 80px;
}

/deep/ .el-upload-list--picture-card .el-upload-list__item-actions {
  font-size: 16px;
}
</style>
