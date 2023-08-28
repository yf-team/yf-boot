import { ElSubMenu, ElMenuItem } from 'element-plus'
import type { RouteMeta } from 'vue-router'
import { useRenderMenuTitle } from './useRenderMenuTitle'
import { useDesign } from '@/hooks/web/useDesign'

export const useRenderMenuItem = (
  allRouters: AppRouteRecordRaw[] = [],
  menuMode: 'vertical' | 'horizontal'
) => {
  const renderMenuItem = (routers?: AppRouteRecordRaw[]) => {
    return (routers || allRouters).map((v) => {
      const meta = (v.meta ?? {}) as RouteMeta
      if (!meta.hidden) {
        // 全部子菜单都使用完整URL地址
        const fullPath = v.path
        const { renderMenuTitle } = useRenderMenuTitle()

        // 如果没有子菜单或目录
        if (!v.children) {
          return (
            <ElMenuItem index={fullPath}>
              {{
                default: () => renderMenuTitle(meta)
              }}
            </ElMenuItem>
          )
        } else {
          const { getPrefixCls } = useDesign()

          const preFixCls = getPrefixCls('menu-popper')
          return (
            <ElSubMenu
              index={fullPath}
              popperClass={
                menuMode === 'vertical' ? `${preFixCls}--vertical` : `${preFixCls}--horizontal`
              }
            >
              {{
                title: () => renderMenuTitle(meta),
                default: () => renderMenuItem(v.children)
              }}
            </ElSubMenu>
          )
        }
      }
    })
  }

  return {
    renderMenuItem
  }
}
