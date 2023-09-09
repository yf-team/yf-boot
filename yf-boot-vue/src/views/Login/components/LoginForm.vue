<template>
  <el-form
    ref="formRef"
    :model="form"
    :rules="rules"
    label-position="top"
    size="large"
    class="dark:(border-1 border-[var(--el-border-color)] border-solid); w-[100%]"
    hide-required-asterisk
  >
    <el-form-item>
      <h2 class="text-2xl font-bold text-center w-[100%]">{{ t('login.loginTitle') }} </h2>
    </el-form-item>

    <el-form-item :label="t('login.username')" prop="userName">
      <el-input
        v-model="form.userName"
        :placeholder="t('login.usernamePlaceholder')"
        clearable
        type="text"
      />
    </el-form-item>

    <el-form-item :label="t('login.password')" prop="password">
      <el-input
        v-model="form.password"
        :placeholder="t('login.passwordPlaceholder')"
        type="password"
        clearable
        :show-password="true"
      />
    </el-form-item>

    <el-form-item :label="t('login.code')" prop="captchaValue">
      <input-captcha v-model="form" style="width: 100%" :placeholder="t('login.codePlaceholder')" />
    </el-form-item>

    <el-form-item>
      <div class="flex justify-between items-center w-[100%]">
        <el-checkbox v-model="remember" :label="t('login.remember')" size="small" />
        <el-link type="primary" :underline="false"> {{ t('login.forgetPassword') }} </el-link>
      </div>
    </el-form-item>

    <el-form-item>
      <div class="w-[100%]">
        <el-button :loading="loading" type="primary" class="w-[100%]" @click="signIn(formRef)">
          {{ t('login.login') }}
        </el-button>
      </div>
      <div class="w-[100%] mt-15px" v-if="siteInfo?.props?.userReg">
        <el-button class="w-[100%]" @click="toRegister"> {{ t('login.noUser') }} </el-button>
      </div>
    </el-form-item>

    <el-form-item style="display: none">
      <el-divider content-position="center">其它登录方式</el-divider>

      <div class="flex justify-between w-[100%]">
        <Icon
          icon="ant-design:github-filled"
          :size="iconSize"
          :color="iconColor"
          :hoverColor="hoverColor"
          class="cursor-pointer ant-icon"
        />
        <Icon
          icon="ant-design:wechat-filled"
          :size="iconSize"
          :color="iconColor"
          :hoverColor="hoverColor"
          class="cursor-pointer ant-icon"
        />
        <Icon
          icon="ant-design:alipay-circle-filled"
          :size="iconSize"
          :color="iconColor"
          :hoverColor="hoverColor"
          class="cursor-pointer ant-icon"
        />
        <Icon
          icon="ant-design:weibo-circle-filled"
          :size="iconSize"
          :color="iconColor"
          :hoverColor="hoverColor"
          class="cursor-pointer ant-icon"
        />
      </div>
    </el-form-item>
  </el-form>
</template>

<script setup lang="tsx">
import { computed, ref, unref, watch } from 'vue'
import { useI18n } from '@/hooks/web/useI18n'
import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'
import { useRouter } from 'vue-router'
import type { RouteLocationNormalizedLoaded } from 'vue-router'
import { useValidator } from '@/hooks/web/useValidator'
import { Icon } from '@/components/Icon'
import { UserLoginType } from '@/api/login/types'
import { FormInstance } from 'element-plus'
import InputCaptcha from '@/components/InputCaptcha/src/InputCaptcha.vue'

const { required } = useValidator()
const emit = defineEmits(['to-register'])

const appStore = useAppStore()
const userStore = useUserStore()
const { currentRoute, replace } = useRouter()
const { t } = useI18n()

const siteInfo = computed(() => appStore.getSiteInfo)

const form = ref<UserLoginType>({
  userName: '',
  password: '',
  captchaKey: '',
  captchaValue: ''
})
const formRef = ref<FormInstance>()

const rules = {
  username: [required()],
  password: [required()],
  captchaValue: [required()]
}
const iconSize = 30
const remember = ref(false)
const loading = ref(false)
const iconColor = '#999'
const hoverColor = 'var(--el-color-primary)'
const redirect = ref<string>('')

watch(
  () => currentRoute.value,
  (route: RouteLocationNormalizedLoaded) => {
    redirect.value = route?.query?.redirect as string
  },
  {
    immediate: true
  }
)

// 登录
const signIn = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl?.validate((isValid) => {
    if (isValid) {
      loading.value = true
      const formData = unref(form)

      userStore
        .login(formData)
        .then(() => {
          loading.value = false
          // 跳转到记录页面或首页
          replace(redirect.value || '/admin/dashboard')
        })
        .catch(() => {
          loading.value = false
        })
    }
  })
}

// 去注册页面
const toRegister = () => {
  emit('to-register')
}
</script>
