package com.dj.mall.api.auth.resource;

import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;

import java.util.List;

/**
 * 资源api
 * @author 86150
 */
public interface ResourceApi {

    /**
     * 获取全部资源信息
     * @return
     * @throws Exception
     */
    List<ResourceDTOResp> getResourceList() throws Exception;

    /**
     * 根据名字查找资源信息
     * @param resourceName
     * @return
     * @throws Exception
     */
    ResourceDTOResp findByName(String resourceName) throws Exception;

    /**
     * 添加
     * @param resourceDTOReq
     * @throws Exception
     */
    void add(ResourceDTOReq resourceDTOReq) throws Exception;

    /**
     * 修改
     * @param map
     * @throws Exception
     */
    void updateResource(ResourceDTOReq map) throws Exception;

    /**
     * 伪删除
     * @param ids
     * @throws Exception
     */
    void updateIsDel(Integer[] ids) throws Exception;

    /**
     * 根据id查找资源信息
     * @param id
     * @return
     * @throws Exception
     */
    ResourceDTOResp getResourceById(Integer id) throws Exception;
}
