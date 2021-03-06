package com.dj.mall.mapper.auth.bo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 BOReq
 */
@Data
public class UserBOReq implements Serializable {

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


    /**
     * 角色展示
     */
    private String roleShow;

    /**
     * 角色id
     */
    private Integer roleId;

}
