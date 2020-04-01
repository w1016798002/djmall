package com.dj.mall.api.auth.role;

import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.role.RoleResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserRoleDTOResp;

import java.util.List;

/**
 * 角色api
 * @author 86150
 */
public interface RoleApi {

    /**
     * 查找角色信息
     * @return
     * @throws Exception
     */
    List<RoleDTOResp> show() throws Exception;

    /**
     * 根据名称查找信息
     * @param roleName
     * @return
     * @throws Exception
     */
    Boolean findByRoleName(String roleName) throws Exception;

    /**
     * 添加角色
     * @param roleDTOReq
     * @throws Exception
     */
    void add(RoleDTOReq roleDTOReq) throws Exception;

    /**
     * 删除角色
     * @param id
     * @throws Exception
     */
    void deleteByRoleId(Integer id) throws Exception;

    /**
     * 角色修改
     * @param roleName
     * @param id
     * @throws Exception
     */
    void updRole(String roleName, Integer id) throws Exception;

    /**
     * 查找信息
     * @param id 根据id
     * @return
     * @throws Exception
     */
    RoleDTOResp getRoleById(Integer id) throws Exception;

    /**
     * 删除角色原关联的资源保存新关联的资源
     * @param roleId
     * @param resourceIds
     * @throws Exception
     */
    void saveRoleResource(Integer roleId, Integer[] resourceIds) throws Exception;

    /**
     * 根据角色id获取角色资源
     * @param roleId
     * @return
     * @throws Exception
     */
    List<RoleResourceDTOResp> getList(Integer roleId) throws Exception;

    /**
     * 查找角色信息
     * @return
     * @throws Exception
     */
    List<RoleDTOResp> getRoleList() throws Exception;

    /**
     * 根据id查找用户角色
     * @param id
     * @return
     * @throws Exception
     */
    UserRoleDTOResp getUserRoleById(Integer id) throws Exception;
}
