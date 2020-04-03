package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.MessageVerifyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 * @author 86150
 */
@RestController
@RequestMapping("/auth/user/")
public class UserController {

    @Reference
    private UserApi userApi;


    /**
     * 登录
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    public ResultModel<Object> login(UserVOReq userVOReq) throws Exception{
        Assert.hasText(userVOReq.getUserName(), SystemConstant.LOGIN_VERIFY);
        Assert.hasText(userVOReq.getUserPwd(), SystemConstant.LOGIN_VERIFY);
        UserDTOResp userDTOResp = userApi.getUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userEntity", userDTOResp);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userVOReq.getUserName(), userVOReq.getUserPwd());
        subject.login(token);
        return new ResultModel<>().success();
    }

    /**
     * 获取yan
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("getSalt")
    public ResultModel<Object> findSalt(UserVOReq userVOReq) throws Exception{
        Assert.hasText(userVOReq.getUserName(), SystemConstant.USSERNAME_EMPTY);
        UserDTOResp userDTOResp = userApi.getUserByUserName(userVOReq.getUserName());
        UserVOResp userVOResp = DozerUtil.map(userDTOResp, UserVOResp.class);
        return new ResultModel<>().success(userVOResp.getSalt());
    }

    /**
     * 用户注册去重
     * @param userVOReq
     * @return
     */
    @RequestMapping("distinct")
    public Boolean findByUsername (UserVOReq userVOReq) throws Exception{
        Boolean distinct = userApi.distinct(DozerUtil.map(userVOReq, UserDTOReq.class));
        return distinct;
    }

    /**
     * 用户注册
     * @param userVOReq
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping("add")
    public ResultModel<Object> add (UserVOReq userVOReq, Integer roleId) throws Exception {
        userApi.saveUser(DozerUtil.map(userVOReq, UserDTOReq.class), roleId);
        return new ResultModel<>().success();
    }

    /**
     * 用户信息展示
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ResultModel<Object> getUserList(UserVOReq userVOReq) throws Exception {
        List<UserDTOResp> userDTORespList = userApi.getUserList(DozerUtil.map(userVOReq, UserDTOReq.class));
        List<UserVOResp> userVOResps = DozerUtil.mapList(userDTORespList, UserVOResp.class);
        return new ResultModel<>().success(userVOResps);
    }

    /**
     * 用户删除
     * @param ids
     * @param isDel
     * @return
     * @throws Exception
     */
    @RequestMapping("delByIds")
    public ResultModel<Object> delByIds(Integer[] ids, Integer isDel) throws Exception {
        userApi.delByIds(ids, isDel);
        return new ResultModel<>().success();
    }

    /**
     * 用户激活
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("updateStatusById")
    public ResultModel<Object> updateStatusById(Integer id, Integer status) throws Exception {
        userApi.updateStatusById(id, status);
        return new ResultModel<>().success();
    }

    /**
     * 授权
     * @param userId
     * @param roleId
     * @return
     */
    @RequestMapping("updateUserRole")
    public ResultModel<Object> updateUserRole(Integer userId, Integer roleId) throws Exception {
        userApi.updateUserRole(userId, roleId);
        return new ResultModel<>().success();
    }

    /**
     * 根据id修改
     * @param userVOReq
     * @return
     */
    @RequestMapping("update")
    public ResultModel<Object> update(UserVOReq userVOReq) throws Exception {
        userApi.updateUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 根据id重置密码
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("resetPwd")
    public ResultModel<Object> resetPwd(Integer id) throws Exception {
        userApi.resetPwdById(id);
        return new ResultModel<>().success();
    }

    /**
     * 修改密码
     * @param userName
     * @param userPwd
     * @return
     * @throws Exception
     */
    @RequestMapping("updatePwd")
    public ResultModel<Object> updatePwd(String userName, String userPwd) throws Exception {
        userApi.updatePwd(userName, userPwd);
        return new ResultModel<>().success();
    }

    /**
     * 手机验证码
     * @param phone
     * @return
     */
    @RequestMapping("sendMessage")
    public ResultModel<Object> sendMessage(String phone) throws Exception {
        userApi.sendMessage(phone);
        return new ResultModel<>().success();
    }

    /**
     * 手机号修改密码
     * @param phone
     * @param userPwd
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("retrievePwd")
    public ResultModel<Object> retrievePwd(String phone, String userPwd, Integer code) throws Exception {
        userApi.retrievePwd(phone, userPwd, code);
        return new ResultModel<>().success();
    }
}
