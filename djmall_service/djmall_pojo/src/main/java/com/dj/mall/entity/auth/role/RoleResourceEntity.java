package com.dj.mall.entity.auth.role;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
/**
 * 角色资源表
 * @author 86150
 */
@Data
@TableName("djmall_auth_role_resource")
public class RoleResourceEntity implements Serializable {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 伪删除  1正常 -1已删除
     */
    private Integer isDel;

}
