package com.dj.mall.pro.auth.impl.role;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.entity.auth.role.RoleEntity;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.mapper.auth.role.RoleMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.role.RoleResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserRoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import com.dj.mall.pro.auth.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色接口实现类
 * @author 86150
 */
@Service
public class RoleApiImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleApi {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 查找角色信息
     * @return
     */
    @Override
    public List<RoleDTOResp> show() throws Exception {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        return DozerUtil.mapList(this.list(queryWrapper), RoleDTOResp.class);
    }

    /**
     * 根据名称查找信息
     * @param roleName
     * @return
     * @throws Exception
     */
    @Override
    public Boolean findByRoleName(String roleName) throws Exception {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        queryWrapper.eq("role_name", roleName);
        RoleEntity roleEntity = this.getOne(queryWrapper);
        if (roleEntity != null) {
            return false;
        }
        return true;
    }

    /**
     * 添加角色
     * @param roleDTOReq
     */
    @Override
    public void add(RoleDTOReq roleDTOReq) {
        this.save(DozerUtil.map(roleDTOReq, RoleEntity.class));
    }

    /**
     * 删除角色
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteByRoleId(Integer id) throws Exception {
        //角色资源表删除
        UpdateWrapper<RoleResourceEntity> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("is_del", SystemConstant.IS_DEL_TRUE);
        updateWrapper1.eq("role_id", id);
        roleResourceService.update(updateWrapper1);
        //用户角色表删除
        UpdateWrapper<UserRoleEntity> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.set("is_del", SystemConstant.IS_DEL_TRUE);
        updateWrapper2.eq("role_id", id);
        userRoleService.update(updateWrapper2);
        //角色表删除
        UpdateWrapper<RoleEntity> updateWrapper3 = new UpdateWrapper<>();
        updateWrapper3.set("is_del", SystemConstant.IS_DEL_TRUE);
        updateWrapper3.eq("id", id);
        this.update(updateWrapper3);
    }

    /**
     * 角色修改
     * @param roleName
     * @param id
     * @throws Exception
     */
    @Override
    public void updRole(String roleName, Integer id) throws Exception {
        UpdateWrapper<RoleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_name", roleName);
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
    }

    /**
     * 查找信息
     * @param id 根据id
     * @return
     * @throws Exception
     */
    @Override
    public RoleDTOResp getRoleById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), RoleDTOResp.class);
    }

    /**
     * 删除角色原关联的资源保存新关联的资源
     * @param roleId
     * @param resourceIds
     * @throws Exception
     */
    @Override
    public void saveRoleResource(Integer roleId, Integer[] resourceIds) throws Exception {
        //先删
        QueryWrapper<RoleResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleResourceService.remove(queryWrapper);
        //遍历数组 新增
        List<RoleResourceEntity> roleResourceList = new ArrayList<>();
        for (Integer resourceId : resourceIds) {
            RoleResourceEntity roleResource = new RoleResourceEntity();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResource.setIsDel(SystemConstant.IS_DEL_FALSE);
            roleResourceList.add(roleResource);
        }
        roleResourceService.saveBatch(roleResourceList);
    }

    @Override
    public List<RoleResourceDTOResp> getList(Integer roleId) throws Exception {
        return DozerUtil.mapList(roleResourceService.getList(roleId), RoleResourceDTOResp.class);
    }

    /**
     * 查找角色信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<RoleDTOResp> getRoleList() throws Exception {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        return DozerUtil.mapList(this.list(queryWrapper), RoleDTOResp.class);
    }

    /**
     * 根据id查找用户角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserRoleDTOResp getUserRoleById(Integer id) throws Exception {
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_FALSE);
        queryWrapper.eq("user_id", id);
        return DozerUtil.map(userRoleService.getOne(queryWrapper), UserRoleDTOResp.class);
    }
}
