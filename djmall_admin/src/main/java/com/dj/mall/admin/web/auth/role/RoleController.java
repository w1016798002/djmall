package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.role.TreeData;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.role.RoleResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色控制层
 * @author 86150
 */
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Reference
    private RoleApi roleApi;

    @Reference
    private ResourceApi resourceApi;

    /**
     * 角色展示
     * @return
     */
    @RequestMapping("show")
    public List<RoleVOResp> show() throws Exception {
        List<RoleDTOResp> roleList = roleApi.show();
        return DozerUtil.mapList(roleList, RoleVOResp.class);
    }

    /**
     * 角色名去重
     * @param roleName
     * @return
     */
    @RequestMapping("findByRoleName")
    public Boolean findByRoleName(String roleName) throws Exception{
        Boolean byRoleName = roleApi.findByRoleName(roleName);
        return byRoleName;
    }

    /**
     * 角色新增
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("add")
    public ResultModel<Object> add(RoleVOReq roleVOReq) throws Exception {
        roleApi.add(DozerUtil.map(roleVOReq, RoleDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 角色删除
     */
    @RequestMapping("delById")
    public ResultModel<Object> delById(Integer id) throws Exception {
        roleApi.deleteByRoleId(id);
        return new ResultModel<>().success();
    }

    /**
     * 角色修改
     */
    @RequestMapping("update")
    public ResultModel<Object> update(String roleName, Integer id) throws Exception {
        roleApi.updRole(roleName, id);
        return new ResultModel<>().success();
    }

    /**
     * 资源表的关联
     *
     * @param roleId
     * @return
     */
    @RequestMapping("roleResources/{roleId}")
    public ResultModel<Object> roleResources(@PathVariable Integer roleId) throws Exception{
        //获取全部的资源表的信息
        List<ResourceDTOResp> resourceList = resourceApi.getResourceList();
        //获取已关联角色的资源的信息
        List<RoleResourceDTOResp> roleResourceList = roleApi.getList(roleId);
        //节点数据
        List<TreeData> treeDataList = new ArrayList<>();
        for (ResourceDTOResp resource : resourceList) {
            TreeData treeData = new TreeData();
            treeData.setId(resource.getId());
            treeData.setName(resource.getResourceName());
            treeData.setPId(resource.getPId());
            for (RoleResourceDTOResp roleResource : roleResourceList) {
                //复选框的回显
                if (resource.getId().equals(roleResource.getResourceId())) {
                    treeData.setChecked(true);
                    break;
                }
            }
            treeDataList.add(treeData);
        }
        return new ResultModel<>().success(treeDataList);
    }

    /**
     * 删除角色原关联的资源保存新关联的资源
     * @param roleId
     * @param resourceIds
     * @return
     * @throws Exception
     */
    @RequestMapping("saveUpdateRole/{roleId}")
    public ResultModel<Object> saveUpdateRole(@PathVariable Integer roleId, Integer[] resourceIds) throws Exception{
        roleApi.saveRoleResource(roleId, resourceIds);
        return new ResultModel<>().success();
    }

}
