package com.dj.mall.api.auth.user;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;

import java.util.List;

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
     *  用户注册
     * @param userDTOReq
     * @param roleId
     * @throws Exception
     */
    void saveUser(UserDTOReq userDTOReq, Integer roleId) throws Exception;

    /**
     * 查询全部用户信息
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    List<UserDTOResp> getUserList(UserDTOReq userDTOReq) throws Exception;

    /**
     * 根据id删除用户
     * @param ids
     * @param isDel
     * @throws Exception
     */
    void delByIds(Integer[] ids, Integer isDel) throws Exception;

    /**
     * 用户激活
     * @param id
     * @param status
     * @throws Exception
     */
    void updateStatusById(Integer id, Integer status) throws Exception;

    /**
     * 授权
     * @param userId
     * @param roleId
     * @throws Exception
     */
    void updateUserRole(Integer userId, Integer roleId) throws Exception;

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     * @throws Exception
     */
    UserDTOResp getUserById(Integer id) throws Exception;

    /**
     * 根据id修改用户信息
     * @param userDTOReq
     * @throws Exception
     */
    void updateUser(UserDTOReq userDTOReq) throws Exception;

    /**
     * 根据id重置密码
     * @param id
     * @throws Exception
     */
    void resetPwdById(Integer id) throws Exception;

    /**
     * 修改用户密码
     * @param userName
     * @param userPwd
     * @throws Exception
     */
    void updatePwd(String userName, String userPwd) throws Exception;

    /**
     * 获取手机验证码
     * @param phone
     * @throws Exception
     */
    void sendMessage(String phone) throws Exception, BusinessException;

    /**
     * 根据手机号修改密码
     * @param phone
     * @param userPwd
     * @param code
     * @throws Exception
     * @throws BusinessException
     */
    void retrievePwd(String phone, String userPwd, Integer code) throws Exception, BusinessException;
}
