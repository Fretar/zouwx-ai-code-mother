package com.zouwx.zouwxaicodemother.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 默认头像地址（COS 存储桶中的默认头像 URL）
     */
    String DEFAULT_USER_AVATAR = "https://lingdaai-1445242189.cos.ap-guangzhou.myqcloud.com/avatars/defaultAvatar.png";
    // endregion
}
