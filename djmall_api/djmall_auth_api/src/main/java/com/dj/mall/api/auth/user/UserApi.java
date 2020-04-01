package com.dj.mall.api.auth.user;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;

/**
 * 用户api
 * @author 86150
 */
public interface UserApi {

    /**
     * 根据用户名和密码获取用户信息
     * @param userDTOReq
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTOResp getUser(UserDTOReq userDTOReq) throws Exception, BusinessException;

    /**
     * 根据用户名查找用户信息
     * @param userName
     * @return
     * @throws Exception
     */
    UserDTOResp getUserByUserName(String userName) throws Exception;

    /**
     * 用户注册去重
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    Boolean distinct(UserDTOReq userDTOReq) throws Exception;

    /**
     * 用户注册
     * @param userDTOReq
     * @throws Exception
     */
    void saveUser(UserDTOReq userDTOReq) throws Exception;

}
