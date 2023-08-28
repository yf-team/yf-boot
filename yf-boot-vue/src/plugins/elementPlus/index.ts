import type { App } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 全局引入ElementPlus
export const setupElementPlus = (app: App) => {
  // 全局引入
  app.use(ElementPlus)
}
