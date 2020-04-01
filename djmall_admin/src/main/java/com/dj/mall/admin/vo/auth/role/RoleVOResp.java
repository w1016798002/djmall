package com.dj.mall.admin.vo.auth.role;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色VOResp
 * @author 86150
 */
@Data
public class RoleVOResp implements Serializable {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 伪删除  1正常 -1已删除
     */
    private Integer isDel;

}
