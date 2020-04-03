package com.dj.mall.entity.auth.user;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dozer.Mapping;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体专
 * @author 86150
 */
@Data
@TableName("djmall_auth_user")
public class UserEntity implements Serializable {

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("userId")
    private Integer id;

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
     * 重置的密码
     */
    private String resetPwd;

    /**
     * 验证码
     */
    private Integer code;

    /**
     * 验证码有效时间
     */
    private Date codeTime;
}
