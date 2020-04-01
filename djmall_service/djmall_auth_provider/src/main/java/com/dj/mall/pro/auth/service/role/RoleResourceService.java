package com.dj.mall.pro.auth.service.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.model.dto.auth.role.RoleResourceDTOResp;

import java.util.List;

/**
 * 角色资源service
 * @author 86150
 */
public interface RoleResourceService extends IService<RoleResourceEntity> {

    /**
     * 获取用户资源
     * @param roleId
     * @return
     * @throws Exception
     */
    List<RoleResourceDTOResp> getList(Integer roleId) throws Exception;
}
