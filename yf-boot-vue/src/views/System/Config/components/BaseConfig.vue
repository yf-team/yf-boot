<template>
  <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
    <el-form-item label="系统名称" prop="siteName">
      <el-input v-model="form.siteName" autocomplete="off" />
    </el-form-item>

    <el-form-item label="登录页图标" prop="loginLogo">
      <el-input v-model="form.loginLogo" autocomplete="off" />
    </el-form-item>

    <el-form-item label="登录页背景" prop="loginBg">
      <el-input v-model="form.loginBg" autocomplete="off" />
    </el-form-item>

    <el-form-item label="后台图标" prop="backLogo">
      <el-input v-model="form.backLogo" autocomplete="off" />
    </el-form-item>

    <el-form-item label="版权信息" prop="copyRight">
      <el-input v-model="form.copyRight" autocomplete="off" />
    </el-form-item>

    <el-form-item label="用户头像" prop="avatar">
      <el-input v-model="form.avatar" autocomplete="off" />
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { ref, reactive, unref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { saveApi, detailApi } from '@/api/sys/user'

const form = ref({})
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

const handleEdit = (row: any) => {
  detailApi({ id: row.id }).then((res) => {
    // 数据
    console.log('data', res.data)
  })
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
      })
    }
  })
}
</script>
