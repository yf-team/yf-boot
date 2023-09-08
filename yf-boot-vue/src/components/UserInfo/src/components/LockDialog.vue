<template>
  <el-dialog
    v-model="dialogVisible"
    width="500px"
    max-height="170px"
    :class="prefixCls"
    :title="dialogTitle"
  >
    <div class="flex flex-col items-center">
      <img :src="userInfo.avatar" alt="" class="w-70px h-70px rounded-[50%]" />
      <span class="text-14px my-10px text-[var(--top-header-text-color)]">{{
        userInfo.realName || userInfo.userName
      }}</span>
    </div>

    <el-form ref="formRef" :model="form" :rules="rules" label-position="left">
      <el-form-item :label="t('lock.lockPassword')" prop="password">
        <el-input v-model="form.password" type="password" :show-password="true" />
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="handleLock(formRef)">{{
          t('lock.lock')
        }}</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup lang="ts">
import { useI18n } from '@/hooks/web/useI18n'
import { ref, unref } from 'vue'
import { reactive, computed } from 'vue'
import { useValidator } from '@/hooks/web/useValidator'
import { FormInstance } from 'element-plus'
import { useDesign } from '@/hooks/web/useDesign'
import { useLockStore } from '@/store/modules/lock'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('lock-dialog')

const { required } = useValidator()

const { t } = useI18n()

const lockStore = useLockStore()

const props = defineProps({
  modelValue: {
    type: Boolean
  }
})

const emit = defineEmits(['update:modelValue'])

const dialogVisible = computed({
  get: () => props.modelValue,
  set: (val) => {
    console.log('set: ', val)
    emit('update:modelValue', val)
  }
})

const dialogTitle = ref(t('lock.lockScreen'))

const rules = reactive({
  password: [required()]
})

const form = ref({
  password: ''
})
const formRef = ref<FormInstance>()

const handleLock = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  await formEl?.validate((isValid) => {
    if (isValid) {
      dialogVisible.value = false
      const formData = unref(form)
      lockStore.setLockInfo({
        isLock: true,
        ...formData
      })
    }
  })
}

const userInfo = computed(() => userStore.getUserInfo)
</script>

<style lang="less" scoped>
:global(.v-lock-dialog) {
  @media (max-width: 767px) {
    max-width: calc(100vw - 16px);
  }
}
</style>
