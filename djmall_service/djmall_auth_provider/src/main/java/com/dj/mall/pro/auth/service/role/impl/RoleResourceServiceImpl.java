package com.dj.mall.pro.auth.service.role.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.mapper.auth.role.RoleResourceMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色资源接口实现类
 * @author 86150
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResourceEntity> implements RoleResourceService {

    /**
     * 获取用户资源
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<RoleResourceDTOResp> getList(Integer roleId) throws Exception {
        QueryWrapper<RoleResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        return DozerUtil.mapList(this.list(queryWrapper), RoleResourceDTOResp.class);
    }
}
