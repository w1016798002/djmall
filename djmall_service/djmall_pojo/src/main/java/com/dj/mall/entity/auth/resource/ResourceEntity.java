package com.dj.mall.entity.auth.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 资源表
 * @author 86150
 */
@Data
@TableName("djmall_auth_resource")
public class ResourceEntity implements Serializable {

    /**
     * 资源id
     */
    @TableId(type = IdType.AUTO)
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
