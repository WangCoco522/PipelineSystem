package com.wico.systemlinkweb.controller;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.wico.systemlinkweb.result.VerifyCodeResp;
import java.util.concurrent.TimeUnit;
import java.io.ByteArrayOutputStream;
import java.util.Base64;



/**
 * 验证码工具类
 */
@RestController
public class VerifyCodeController {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码
     *
     * 方法一 ShearCaptcha
     * 图片格式
     * session存储
     * 接口需添加白名单放行
     *
     * @param request HttpServletRequest
     */
    @GetMapping("/verify")
    public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 40, 5, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        shearCaptcha.write(response.getOutputStream());
        //获取验证码中的文字内容
        request.getSession().setAttribute("verifyCode", shearCaptcha.getCode());
    }
}

