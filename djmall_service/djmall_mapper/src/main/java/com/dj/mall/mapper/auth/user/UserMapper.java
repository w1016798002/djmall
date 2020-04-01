package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.user.UserEntity;
import com.dj.mall.mapper.auth.bo.user.UserBOReq;
import com.dj.mall.mapper.auth.bo.user.UserBOResp;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 用户Mapper
 * @author 86150
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 查找用户权限集合
     * @param userId
     * @return
     * @throws DataAccessException
     */
    List<ResourceEntity> getUserResourceByUserId(Integer userId) throws DataAccessException;

    /**
     * 查找用户信息
     * @param userBOReq
     * @return
     * @throws DataAccessException
     */
    List<UserBOResp> getUserList(UserBOReq userBOReq) throws DataAccessException;
}
