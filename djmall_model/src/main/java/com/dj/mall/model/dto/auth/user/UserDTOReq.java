package com.dj.mall.model.dto.auth.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户DTO-req对象
 * @author 86150
 */
@Data
public class UserDTOReq implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 级别  1为普通用户 2为管理员 3为经理
     */
    private Integer level;

    /**
     * 性别  1男2女
     */
    private Integer sex;

    /**
     * 状态 -1未激活   1 已激活
     */
    private Integer status;

    /**
     * 伪删除  1正常 -1已删除
     */
    private Integer isDel;

    /**
     * yan
     */
    private String salt;
}
