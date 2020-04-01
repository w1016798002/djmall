package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 资源page处理类
 * @author 86150
 */
@Controller
@RequestMapping("/resource/")
public class ResourcePageController {

    @Reference
    private ResourceApi resourceApi;

    /**
     * 去展示
     */
    @RequestMapping("toList")
    @RequiresPermissions(value = "RESOURCE_MANAGER")
    public String toList() {
        return "resource/list";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd/{pId}")
    public String toAdd(@PathVariable Integer pId, Model model) {
        model.addAttribute("pId", pId);
        return "resource/add";
    }

    /**
     * 去修改页面
     */
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, Model model) throws Exception {
        ResourceDTOResp resource = resourceApi.getResourceById(id);
        model.addAttribute("resource", DozerUtil.map(resource, ResourceVOResp.class));
        return "resource/update";
    }
}
