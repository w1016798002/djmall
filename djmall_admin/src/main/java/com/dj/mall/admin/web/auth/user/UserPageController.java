package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserRoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.auth.user.UserRoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户page处理层
 * @author 86150
 */
@Controller
@RequestMapping("/auth/user/")
public class UserPageController {

    @Reference
    private RoleApi roleApi;

    @Reference
    private UserApi userApi;

    /**
     * 去登录
     */
    @RequestMapping("toLogin")
    private String toLogin() {
        return "user/login";
    }

    /**
     * 去添加
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    private String toAdd(Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        List<RoleDTOResp> roleList = roleApi.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("salt", salt);
        return "user/add";
    }

    /**
     * 去用户展示页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toList")
    private String toList1(Model model) throws Exception {
        List<RoleDTOResp> roleList = roleApi.getRoleList();
        model.addAttribute("roleList", roleList);
        return "user/list";
    }

    /**
     * 去授权页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toUpdateRole/{id}")
    public String toUpdateRole(@PathVariable Integer id, Model model) throws Exception {
        UserRoleDTOResp userRoleDTOResp = roleApi.getUserRoleById(id);
        model.addAttribute("userRole", DozerUtil.map(userRoleDTOResp, UserRoleVOResp.class));
        List<RoleDTOResp> roleList = roleApi.getRoleList();
        model.addAttribute("roleList", DozerUtil.mapList(roleList, RoleVOResp.class));
        return "user/update_role";
    }

    /**
     * 去修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) throws Exception {
        UserDTOResp user = userApi.getUserById(id);
        model.addAttribute("user", DozerUtil.map(user, UserVOResp.class));
        return "user/update";
    }

    /**
     * 去修改密码
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping("toUpdatePwd/{userName}")
    public String toUpdatePwd(@PathVariable String userName, Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("userName", userName);
        model.addAttribute("salt", salt);
        return "user/update_pwd";
    }
}
