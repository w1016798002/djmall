package com.dj.mall.model.dto.auth.role;

import lombok.Data;
import java.io.Serializable;

/**
 * 角色资源DTOReq
 * @author 86150
 */
@Data
public class RoleResourceDTOReq implements Serializable {

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
