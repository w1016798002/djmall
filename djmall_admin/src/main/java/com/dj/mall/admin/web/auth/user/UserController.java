package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
     * @return
     */
    @RequestMapping("add")
    public ResultModel<Object> add (UserVOReq userVOReq) throws Exception{
        userApi.saveUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success();
    }

}
