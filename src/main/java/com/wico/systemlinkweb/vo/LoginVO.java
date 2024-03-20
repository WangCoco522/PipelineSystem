package com.wico.systemlinkweb.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    public boolean isValid() {
        return StringUtils.hasText(username) && StringUtils.hasText(password) && StringUtils.hasText(captcha);
    }

}
