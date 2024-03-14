package com.wico.systemlinkweb.types;

import lombok.Getter;

/**
 * @author Neosgao
 * @date 16:17 2022/12/9
 **/
@Getter
public enum ResultType {
    USER_LOGIN_INFO_EMPTY(100001, "用户名或密码为空"),
    USER_NOT_EXISTED(100002, "用户不存在"),
    USER_PASSWORD_ERROR(100003, "密码错误"),
    USER_EXISTED(100004, "用户已存在"),
    USER_REGISTER_INFO_EMPTY(100005, "用户注册信息不完整"),
    USER_REGISTER_FAILED(100006, "用户注册失败"),
    USER_TOKEN_INVALIDED(100007, "登录凭证失效，请重新登录"),
    USER_TOKEN_EXPIRED(100008, "登录凭证过期，请重新登录"),
    USER_ENABLE_ERROR(100009, "启用失败"),
    USER_UNABLE_ERROR(100010, "禁用失败"),
    USER_NEW_PASSWORD_EMPTY(100011, "新密码为空"),
    USER_UNABLE(100012, "账户未启用,请联系管理员");
    ;

    private final int code;
    private final String msg;

    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
