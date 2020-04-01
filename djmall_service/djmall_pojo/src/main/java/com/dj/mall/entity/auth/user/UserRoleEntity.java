package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色表
 * @author 86150
 */
@Data
@TableName("djmall_auth_user_role")
public class UserRoleEntity implements Serializable {

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
