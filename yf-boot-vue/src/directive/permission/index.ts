import type { App } from 'vue'
import { useAppStoreWithOut } from '@/store/modules/app'

const appStore = useAppStoreWithOut()

export const setupPermission = (app: App<Element>) => {
  app.directive('permission', {
    beforeMount(el, binding) {
      const { value } = binding

      // 空白表示无需权限
      if (value.join('') === '') {
        return true
      }

      // 获取权限标识
      const permissions = appStore.userInfo.permissions || []

      // 判断功能是否存在
      const hasFunc = permissions.some((func) => {
        return value.includes(func)
      })

      // 有权限
      if (hasFunc) {
        return true
      }

      // 隐藏没有的权限
      el.style.display = 'none'
    }
  })
}
