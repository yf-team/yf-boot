package com.yf.system.modules.depart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.ability.redis.service.RedisService;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.enums.DataScope;
import com.yf.base.utils.DeptCodeGen;
import com.yf.system.modules.depart.dto.SysDepartDTO;
import com.yf.system.modules.depart.dto.request.DepartQueryReqDTO;
import com.yf.system.modules.depart.dto.request.DepartSortReqDTO;
import com.yf.system.modules.depart.dto.response.SysDepartTreeDTO;
import com.yf.system.modules.depart.entity.SysDepart;
import com.yf.system.modules.depart.mapper.SysDepartMapper;
import com.yf.system.modules.depart.service.SysDepartService;
import com.yf.system.modules.user.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 部门信息业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-09-02 17:25
*/
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements SysDepartService {


    /**
     * 0标识为顶级分类
     */
    private static final String ROOT_TAG = "0";
    private static final String LOCK_DEPT = "sys:lock:dept";

    @Autowired
    private RedisService redisService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysDepartDTO reqDTO) {

        // 定时锁
        boolean lock = redisService.tryLock(LOCK_DEPT, 60000L);
        if(!lock){
            throw new ServiceException("系统正忙，请稍候...");
        }


        try {
            SysDepart entity = new SysDepart();
            BeanMapper.copy(reqDTO, entity);

            if (StringUtils.isBlank(reqDTO.getId())) {
                this.fillCode(entity);
            } else {
                reqDTO.setSort(null);
                reqDTO.setDeptCode(null);
            }

            this.saveOrUpdate(entity);

        }finally {
            // 移除缓存
            redisService.del(LOCK_DEPT);
        }


    }


    @Override
    public List<SysDepartTreeDTO> findTree(boolean self) {

        DepartQueryReqDTO reqDTO = new DepartQueryReqDTO();

        // 构建权限
        if(self) {
            this.appendDeptQuery(reqDTO);
        }

        // 查询参数
        List<SysDepartTreeDTO> treeData = baseMapper.tree(reqDTO);
        return treeData;
    }


    @Override
    public void sort(DepartSortReqDTO reqDTO) {

        // 只能进行交换排序
        SysDepart form = this.getById(reqDTO.getForm());
        SysDepart to = this.getById(reqDTO.getTo());

        int fromSort = form.getSort();
        int toSort = to.getSort();

        form.setSort(toSort);
        to.setSort(fromSort);
        this.updateById(form);
        this.updateById(to);
    }

    @Override
    public String syncDepart(String str) {

        String [] arr = str.split(",");


        // 如：云帆互联,产品研发部,技术部
        List<String> subs = new ArrayList<>();

        // 默认0
        String parentId = ROOT_TAG;
        String deptCode = null;

        for(int i=0; i<arr.length; i++){

            // 自上往下搜索
            subs.add(arr[i]);


            SysDepart depart = this.findLastChild(subs);

            if(depart!=null){
                parentId = depart.getId();
                deptCode = depart.getDeptCode();
            }else{

                // 循环剩下的
                List<String> left = new ArrayList<>();

                // 剩余要添加的子部门
                for(int j=i;j<arr.length;j++){
                    left.add(arr[j]);
                }

                // 创建剩余的子部门
                deptCode = this.createSubs(parentId, left);
                break;
            }
        }

        return deptCode;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {
        //查询条件
        QueryWrapper<SysDepart> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysDepart::getParentId, ids);
        long count = this.count(wrapper);
        if(count > 0){
            throw new ServiceException("请先删除下级部门！");
        }

        this.removeByIds(ids);
    }

    /**
     * 从上往下查找部门子节点
     * @param list
     * @return
     */
    private SysDepart findLastChild(List<String> list){

        String parentId = ROOT_TAG;

        for(int i=0;i<list.size();i++){
            SysDepart depart = this.findDeptByName(parentId, list.get(i));

            // 找不到返回空
            if(depart == null){
                return null;
            }

            parentId = depart.getId();

            // 找到了最底级
            if(i>=list.size()-1){
                return depart;
            }
        }

        return null;
    }

    /**
     * 创建下级并返回部门ID
     * @param parentId
     * @param list
     * @return
     */
    private String createSubs(String parentId, List<String> list){


        String deptCode = null;

        for (String name: list){

            SysDepart entity = new SysDepart();
            entity.setDeptName(name);
            entity.setParentId(parentId);

            this.fillCode(entity);
            this.save(entity);

            parentId = entity.getId();
            deptCode = entity.getDeptCode();
        }

        return deptCode;
    }

    /**
     * 根据名称查找部门
     * @param name
     * @return
     */
    public SysDepart findDeptByName(String parentId, String name) {
        QueryWrapper<SysDepart> wrapper = new QueryWrapper<>();

        // 同级排序
        wrapper.lambda()
                .eq(SysDepart::getDeptName, name)
                .eq(SysDepart::getParentId, parentId);

        return this.getOne(wrapper, false);

    }

    /**
     * 填充部门编号
     * @param depart
     */
    private void fillCode(SysDepart depart){

        // 前缀
        String code = "";

        if(StringUtils.isNotBlank(depart.getParentId())
                && !ROOT_TAG.equals(depart.getParentId())){
            SysDepart parent = this.getById(depart.getParentId());
            code = parent.getDeptCode();
        }

        QueryWrapper<SysDepart> wrapper = new QueryWrapper<>();

        // 同级排序
        wrapper.lambda()
                .eq(SysDepart::getParentId, depart.getParentId())
                .orderByDesc(SysDepart::getSort);
        wrapper.last("LIMIT 1");
        SysDepart max = this.getOne(wrapper, false);

        if(max !=null){
            code += DeptCodeGen.gen(max.getSort()+1);
            depart.setSort(max.getSort()+1);
        }else{
            code += DeptCodeGen.gen(1);
            depart.setSort(1);
        }

        depart.setDeptCode(code);
    }

    /**
     * 构建特有的部门查看权限
     * @param params
     */
    private void appendDeptQuery(DepartQueryReqDTO params){

        // 用户部门
        String code = UserUtils.departCode();
        Integer scope = UserUtils.getDataScope();

        // 非全部权限，只过滤自己部门
        if(!DataScope.SCOPE_ALL.equals(scope)){

            int length = code.length() / 3;
            StringBuffer sb = null;
            for(int i=0;i<length;i++){
                String sub = code.substring(0,(i+1)*3);
                if(sb == null){
                    sb = new StringBuffer();
                }else{
                    sb.append(",");
                }
                sb.append("'"+sub+"'");
            }

            params.setDeptCodes(sb.toString());

            // 本部门及以下
            if(DataScope.SCOPE_DEPT_DOWN.equals(scope)){
                params.setLikeCode(code);
            }
        }
    }
}
