package com.dj.mall.model.constant;

/**
 * @author 86150
 */
public class SystemConstant {
	/**
	 * 验证码错误
	 */
	public static final String CODE_ERROR = "验证码错误";

	/**
	 * 验证码失效
	 */
	public static final String FALSE_CODE = "验证码失效";

	/**
	 * 用户名或密码不得为空
	 */
	public static final String LOGIN_VERIFY = "用户名或密码不得为空";

	/**
	 * 用户名或密码错误
	 */
	public static final String LOGIN_ERROR = "用户名或密码错误";

	/**
	 * 用户名不得为空
	 */
    public static final String USSERNAME_EMPTY = "用户名不得为空";

	/**
	 * 该用户已被删除
	 */
	public static final String DEL = "该用户已被删除";

	/**
	 * 该用户未被激活，请联系管理员激活
	 */
	public static final String NOT_ACTIVE = "该用户未被激活，请联系管理员激活";

	/**
	 * 用户登录信息
	 */
	public static final String USER_SESSION = "userEntity";

	/**
	 * 菜单
	 */
	public static final Integer MENU = 1;

	/**
	 * 已删除-1
	 */
	public static final Integer IS_DEL_TRUE = -1;

	/**
	 * 未删除 1
	 */
	public static final Integer IS_DEL_FALSE = 1;

	/**
	 * 返回状态吗
	 */
	public static final Integer ERROR_CODE = 300;

	/**
	 * 您的密码已被重置，将前往修改密码页面
	 */
	public static final String ERROR_MSG = "您的密码已被重置，将前往修改密码页面";

	/**
	 * 手机号暂无绑定信息，请检查您的手机号
	 */
	public static final String PHONE_NOT = "手机号暂无绑定信息，请检查您的手机号";

	/**
	 * 激活状态 1
	 */
	public static final Integer ACTIVATE = 1;
}
