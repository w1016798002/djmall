package com.dj.mall.admin.vo.auth.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色VOReq
 * @author 86150
 */
@Data
public class UserRoleVOReq implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 伪删除  1正常 -1已删除
     */
    private Integer isDel;

}
