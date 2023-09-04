<script setup lang="ts">
import { useAppStore } from '@/store/modules/app'
import { computed } from 'vue'
import { ContentWrap } from '@/components/ContentWrap'
const appStore = useAppStore()
const userInfo = computed(() => appStore.getUserInfo)
const siteInfo = computed(() => appStore.getSiteInfo)
</script>

<template>
  <ContentWrap v-loading="loading" element-loading-text="加载中...">
    <div> 欢迎使用{{ siteInfo.siteName }} </div>
  </ContentWrap>
</template>
