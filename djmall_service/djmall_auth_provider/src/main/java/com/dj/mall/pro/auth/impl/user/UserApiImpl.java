package com.dj.mall.pro.auth.impl.user;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.user.UserEntity;
import com.dj.mall.mapper.auth.user.UserMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

/**
 * 用户接口实现类
 * @author 86150
 */
@Service
public class UserApiImpl extends ServiceImpl<UserMapper, UserEntity> implements UserApi {

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
        if (userEntity.equals(SystemConstant.NULL)) {
            throw new BusinessException(SystemConstant.LOGIN_ERROR);
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
        if (userEntity .equals(SystemConstant.NULL)) {
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
    public void saveUser(UserDTOReq userDTOReq) throws Exception {
        userDTOReq.setCreateTime(new Date());
        this.save(DozerUtil.map(userDTOReq, UserEntity.class));
    }
}
