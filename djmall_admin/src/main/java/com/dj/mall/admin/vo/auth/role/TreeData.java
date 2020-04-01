package com.dj.mall.admin.vo.auth.role;

import lombok.Data;

import java.io.Serializable;

/**
 * tree数据
 * @author 86150
 */
@Data
public class TreeData implements Serializable {

    /**
     * 节点id
     */
    private Integer id;

    /**
     * 节点父id
     */
    private Integer pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否展开
     */
    private Boolean open = true;

    /**
     * 默认勾选
     */
    private Boolean checked;

}
