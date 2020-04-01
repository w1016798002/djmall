package com.dj.mall.admin.web.auth.role;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 角色page处理类
 * @author 86150
 */
@Controller
@RequestMapping("/role/")
public class RolePageController {

    @Reference
    private RoleApi roleApi;

    @Reference
    private ResourceApi resourceApi;


    @RequestMapping("toList")
    @RequiresPermissions(value = "ROLE_MANAGER")
    private String toList() {
        return "role/list";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "role/add";
    }

    /**
     * 去修改页面
     */
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, Model model) throws Exception{
        RoleDTOResp role = roleApi.getRoleById(id);
        model.addAttribute("role", DozerUtil.map(role, RoleVOResp.class));
        return "role/update";
    }

    /**
     * 去关联资源数据
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("toRoleResList")
    public String toResource(Integer roleId, Model model) throws Exception{
        model.addAttribute("roleId", roleId);
        return "role/role_res_list";
    }

}
