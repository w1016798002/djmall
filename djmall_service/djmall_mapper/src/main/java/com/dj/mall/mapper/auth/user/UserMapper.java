package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.user.UserEntity;

import java.util.List;

/**
 * 用户Mapper
 * @author 86150
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 查找用户权限集合
     * @param userId 根据用户id
     * @return
     */
    List<ResourceEntity> getUserResourceByUserId(Integer userId);
}
