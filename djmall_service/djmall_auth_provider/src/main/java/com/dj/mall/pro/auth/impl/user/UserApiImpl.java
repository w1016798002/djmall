package com.dj.mall.pro.auth.impl.user;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.user.UserEntity;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.mapper.auth.bo.user.UserBOReq;
import com.dj.mall.mapper.auth.user.UserMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.JavaEmailUtils;
import com.dj.mall.model.util.MessageVerifyUtils;
import com.dj.mall.model.util.PasswordSecurityUtil;
import com.dj.mall.pro.auth.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户接口实现类
 * @author 86150
 */
@Service
public class UserApiImpl extends ServiceImpl<UserMapper, UserEntity> implements UserApi {


    @Autowired
    private UserRoleService userRoleService;

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param userDTOReq 用户对象
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUser(UserDTOReq userDTOReq) throws Exception, BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_pwd", userDTOReq.getUserPwd());
        queryWrapper.or(i -> i.eq("user_name", userDTOReq.getUserName())
                .or().eq("email", userDTOReq.getUserName())
                .or().eq("phone", userDTOReq.getUserName()));
        UserEntity userEntity = this.getOne(queryWrapper);
        if (userEntity == null) {
            throw new BusinessException(SystemConstant.LOGIN_ERROR);
        }
        if (!StringUtils.isEmpty(userEntity.getResetPwd()) && userEntity.getResetPwd().equals(userEntity.getUserPwd())) {
            throw new BusinessException(SystemConstant.ERROR_CODE, SystemConstant.ERROR_MSG);
        }
        if (!userEntity.getIsDel().equals(SystemConstant.IS_DEL_FALSE)) {
            throw  new BusinessException(SystemConstant.DEL);
        }
        if (!userEntity.getStatus().equals(SystemConstant.MENU)) {
            throw  new BusinessException(SystemConstant.NOT_ACTIVE);
        }
        UserDTOResp userDTOResp = DozerUtil.map(userEntity, UserDTOResp.class);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("last_login_time", new Date());
        updateWrapper.eq("id", userDTOResp.getUserId());
        this.update(updateWrapper);
        //获取用户的权限集合
        List<ResourceEntity> userResourceList = getBaseMapper().getUserResourceByUserId(userDTOResp.getUserId());
        userDTOResp.setPermissionList(DozerUtil.mapList(userResourceList, ResourceDTOResp.class));
        return userDTOResp;
    }

    /**
     * 根据用户名查找信息
     * @param userName
     * @return
     */
    @Override
    public UserDTOResp getUserByUserName(String userName) throws Exception{
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName)
                .or().eq("phone", userName)
                .or().eq("email", userName);
        UserEntity userEntity = this.getOne(queryWrapper);
        return DozerUtil.map(userEntity, UserDTOResp.class);
    }

    /**
     * 用户注册去重
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public Boolean distinct(UserDTOReq userDTOReq) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userDTOReq.getUserName())) {
            queryWrapper.eq("user_name", userDTOReq.getUserName());
        }
        if (!StringUtils.isEmpty(userDTOReq.getPhone())) {
            queryWrapper.eq("phone", userDTOReq.getPhone());
        }
        if (!StringUtils.isEmpty(userDTOReq.getEmail())) {
            queryWrapper.eq("email", userDTOReq.getEmail());
        }
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        UserEntity userEntity = this.getOne(queryWrapper);
        if (userEntity == null) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     *
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public void saveUser(UserDTOReq userDTOReq, Integer roleId) throws Exception {
        //用户表添加
        userDTOReq.setCreateTime(new Date());
        UserEntity user = DozerUtil.map(userDTOReq, UserEntity.class);
        this.save(user);
        //用户资源表添加
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setIsDel(SystemConstant.IS_DEL_FALSE);
        userRoleEntity.setRoleId(roleId);
        userRoleEntity.setUserId(user.getId());
        userRoleService.save(userRoleEntity);
    }

    /**
     * 查询全部用户信息
     *
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTOResp> getUserList(UserDTOReq userDTOReq) throws Exception {
        return DozerUtil.mapList(getBaseMapper().getUserList(DozerUtil.map(userDTOReq, UserBOReq.class)), UserDTOResp.class);
    }

    /**
     * 根据id删除用户
     *
     * @param ids
     * @param isDel
     * @throws Exception
     */
    @Override
    public void delByIds(Integer[] ids, Integer isDel) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", isDel);
        updateWrapper.in("id", ids);
        this.update(updateWrapper);
    }

    /**
     * 用户激活
     *
     * @param id
     * @param status
     * @throws Exception
     */
    @Override
    public void updateStatusById(Integer id, Integer status) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", status);
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
    }

    /**
     * 授权
     *
     * @param userId
     * @param roleId
     * @throws Exception
     */
    @Override
    public void updateUserRole(Integer userId, Integer roleId) throws Exception {
        //用户角色表修改
        UpdateWrapper<UserRoleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_id", roleId);
        updateWrapper.eq("user_id", userId);
        userRoleService.update(updateWrapper);
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUserById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), UserDTOResp.class);
    }

    /**
     * 根据id修改用户信息
     *
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public void updateUser(UserDTOReq userDTOReq) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_name", userDTOReq.getUserName())
                .set("phone", userDTOReq.getPhone())
                .set("email", userDTOReq.getEmail())
                .set("sex", userDTOReq.getSex());
        updateWrapper.eq("id", userDTOReq.getUserId());
        this.update(updateWrapper);
    }

    /**
     * 根据id重置密码
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void resetPwdById(Integer id) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        String resetPwd = PasswordSecurityUtil.generateRandom(6);
        String md5Pwd = PasswordSecurityUtil.enCode32(resetPwd);
        String md5ResetPwd = PasswordSecurityUtil.enCode32(md5Pwd + salt);
        UserEntity user = this.getById(id);
        //发送邮件
        DateFormat df = DateFormat.getDateTimeInstance();
        JavaEmailUtils.sendEmail(user.getEmail(), "重置密码",
                "您的密码已被管理员于"+df.format(new Date())+"时重置为"+resetPwd+".为了您的账户安全，请及时修改。</br>" +
                        "<a href='http://localhost:8081/admin/auth/user/toLogin'>点我去登陆</a><br>"
        );
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("user_pwd", md5ResetPwd)
                .set("reset_pwd", md5ResetPwd)
                .set("salt", salt);
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
    }

    /**
     * 修改用户密码
     *
     * @param userName
     * @param userPwd
     * @throws Exception
     */
    @Override
    public void updatePwd(String userName, String userPwd) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_pwd", userPwd);
        updateWrapper.eq("user_name", userName).or().eq("email", userName).or().eq("phone", userName);
        this.update(updateWrapper);
    }

    /**
     * 获取手机验证码
     *
     * @param phone
     * @throws Exception
     */
    @Override
    public void sendMessage(String phone) throws Exception, BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        UserEntity userEntity = this.getOne(queryWrapper);
        if (userEntity == null) {
            throw new BusinessException(SystemConstant.PHONE_NOT);
        }
        if (userEntity.getIsDel().equals(SystemConstant.IS_DEL_TRUE)) {
            throw new BusinessException(SystemConstant.DEL);
        }
        //时间加减
        Calendar cal = Calendar.getInstance();
        //设置起时间
        cal.setTime(new Date());
        //增加分钟
        cal.add(Calendar.MINUTE, 2);
        int newCode = MessageVerifyUtils.getNewcode();
        //根据手机号修改验证码
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("code_time", cal.getTime())
                .set("code", newCode);
        updateWrapper.eq("phone", phone);
        this.update(updateWrapper);
        MessageVerifyUtils.sendSms(String.valueOf(userEntity.getPhone()), String.valueOf(newCode));
    }

    /**
     * 根据手机号修改密码
     *
     * @param phone
     * @param userPwd
     * @param code
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void retrievePwd(String phone, String userPwd, Integer code) throws Exception, BusinessException {
        //获取查到的数据
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code)
                .eq("phone", phone);
        UserEntity user = this.getOne(queryWrapper);
        //判断数据库中是否存在此条数据
        if(null == user) {
            throw  new BusinessException(SystemConstant.CODE_ERROR);
        }
        //验证码已失效
        if(System.currentTimeMillis() > user.getCodeTime().getTime()) {
            throw  new BusinessException(SystemConstant.FALSE_CODE);
        }
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_pwd", userPwd);
        updateWrapper.eq("phone", phone);
        this.update(updateWrapper);
        //发送邮件
        DateFormat df = DateFormat.getDateTimeInstance();
        JavaEmailUtils.sendEmail(user.getEmail(), "修改密码", "您的账户"+user.getNickName()+"，于"+df.format(new Date())+"时进行密码修改成功。");
    }
}
