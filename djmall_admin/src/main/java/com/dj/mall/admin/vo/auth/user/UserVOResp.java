package com.dj.mall.admin.vo.auth.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户VOResp
 * @author 86150
 */
@Data
public class UserVOResp implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 重置的密码
     */
    private String resetPwd;

}
