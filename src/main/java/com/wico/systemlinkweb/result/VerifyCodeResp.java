package com.wico.systemlinkweb.result;
import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeResp implements Serializable {
    /**
     * header头参数：Captcha-Key
     */
    private String captchaKey;

    /**
     * 验证码图片
     */
    private String captchaImg;
}

