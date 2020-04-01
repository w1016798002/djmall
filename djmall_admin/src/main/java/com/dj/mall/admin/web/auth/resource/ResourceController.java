package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOReq;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源控制层
 * @author 86150
 */
@RestController
@RequestMapping("/resource/")
public class ResourceController {

    @Reference
    private ResourceApi resourceApi;

    /**
     * 左侧菜单展示
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public List<ResourceVOResp> left(HttpSession session) throws Exception {
        //当前登录用户
        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        List<ResourceDTOResp> menuList = new ArrayList<>();
        for (ResourceDTOResp resource : userDTOResp.getPermissionList()) {
            if (resource.getResourceType().equals(SystemConstant.MENU)) {
                menuList.add(resource);
            }
        }
        return DozerUtil.mapList(menuList, ResourceVOResp.class);
    }

    /**
     * 资源展示
     * @return
     * @throws Exception
     */
    @RequestMapping("show")
    public List<ResourceVOResp> show() throws Exception {
        List<ResourceDTOResp> resourceList = resourceApi.getResourceList();
        return DozerUtil.mapList(resourceList, ResourceVOResp.class);
    }

    /**
     * 资源名去重
     * @param resourceName
     * @return
     * @throws Exception
     */
    @RequestMapping("findByResourceName")
    public Boolean findByName(String resourceName) throws Exception {
        ResourceDTOResp resourceDTOResp = resourceApi.findByName(resourceName);
        if (resourceDTOResp != null) {
            return false;
        }
        return true;
    }

    /**
     * 资源新增
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("add")
    public ResultModel<Object> add(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.add(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 资源修改
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("update")
    public ResultModel<Object> update(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.updateResource(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 资源删除
     * @param ids
     * @return
     */
    @RequestMapping("delById")
    public ResultModel<Object> delById(Integer[] ids) throws Exception {
        resourceApi.updateIsDel(ids);
        return new ResultModel<>().success();
    }


}
