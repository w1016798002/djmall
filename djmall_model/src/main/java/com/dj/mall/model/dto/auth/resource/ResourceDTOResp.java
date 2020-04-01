package com.dj.mall.model.dto.auth.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * 资源DTOResp
 * @author 86150
 */
@Data
public class ResourceDTOResp implements Serializable {

    /**
     * 资源id
     */
    private Integer id;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 父级id
     */
    private Integer pId;

    /**
     * 是否删除 1，正常 -1 已删除
     */
    private Integer isDel;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 1为菜单 2按钮
     */
    private Integer resourceType;


}
