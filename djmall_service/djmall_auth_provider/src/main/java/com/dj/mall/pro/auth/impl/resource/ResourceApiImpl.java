package com.dj.mall.pro.auth.impl.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.mapper.auth.resource.ResourceMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 资源接口实现类
 * @author 86150
 */
@Service
public class ResourceApiImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceApi {

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 获取全部资源信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ResourceDTOResp> getResourceList() throws Exception {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        return DozerUtil.mapList(this.list(queryWrapper), ResourceDTOResp.class);
    }

    /**
     * 根据名字查找资源信息
     * @param resourceName
     * @return
     */
    @Override
    public ResourceDTOResp findByName(String resourceName) throws Exception {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("resource_name", resourceName);
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        return DozerUtil.map(this.getOne(queryWrapper), ResourceDTOResp.class);
    }

    /**
     * 添加
     * @param resourceDTOReq
     * @throws Exception
     */
    @Override
    public void add(ResourceDTOReq resourceDTOReq) throws Exception {
        resourceDTOReq.setResourceCode(resourceDTOReq.getResourceCode().toUpperCase());
        this.save(DozerUtil.map(resourceDTOReq, ResourceEntity.class));
    }

    /**
     * 修改
     * @param map
     * @throws Exception
     */
    @Override
    public void updateResource(ResourceDTOReq map) throws Exception {
        UpdateWrapper<ResourceEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("resource_name", map.getResourceName()).set("url", map.getUrl());
        updateWrapper.eq("id", map.getId());
        this.update(updateWrapper);
    }

    /**
     * 伪删除
     * @param ids
     * @throws Exception
     */
    @Override
    public void updateIsDel(Integer[] ids) throws Exception {
        //资源删除
        UpdateWrapper<ResourceEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", SystemConstant.IS_DEL_TRUE);
        updateWrapper.in("id", ids);
        this.update(updateWrapper);
        //角色资源删除
        UpdateWrapper<RoleResourceEntity> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.in("resource_id", ids);
        roleResourceService.remove(updateWrapper1);
    }

    /**
     * 根据id查找资源信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ResourceDTOResp getResourceById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), ResourceDTOResp.class);
    }
}
