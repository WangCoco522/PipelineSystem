//package com.wico.systemlinkweb.security.handle;
//
//import com.alibaba.fastjson.JSON;
//import com.wico.systemlinkweb.result.CodeMsg;
//import com.wico.systemlinkweb.result.Result;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author XuCheng
// * @Date 2020-7-1 16:00
// */
//@Component
//public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        //返回json数据
//        Result result = null;
//        if (e instanceof AccountExpiredException) {
//            //账号过期
//            result = Result.error(CodeMsg.USER_ACCOUNT_EXPIRED);
//        } else if (e instanceof BadCredentialsException) {
//            //密码错误
//            result = Result.error(CodeMsg.PASSWORD_ERROR);
//        } else if (e instanceof CredentialsExpiredException) {
//            //密码过期
//            result = Result.error(CodeMsg.USER_CREDENTIALS_EXPIRED);
//        } else if (e instanceof DisabledException) {
//            //账号不可用
//            result = Result.error(CodeMsg.USER_ERROR);
//        } else if (e instanceof LockedException) {
//            //账号锁定
//            result = Result.error(CodeMsg.USER_ACCOUNT_LOCKED);
//        } else if (e instanceof InternalAuthenticationServiceException) {
//            //用户不存在
//            result = Result.error(CodeMsg.USER_EMPTY);
//        }else{
//            //其他错误
//            result = Result.error(CodeMsg.USER_ERROR);
//        }
//        httpServletResponse.setContentType("text/json;charset=utf-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
//    }
//}
