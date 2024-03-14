package com.wico.systemlinkweb.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public boolean isValid() {
        return StringUtils.hasText(username) && StringUtils.hasText(password);
    }
}
