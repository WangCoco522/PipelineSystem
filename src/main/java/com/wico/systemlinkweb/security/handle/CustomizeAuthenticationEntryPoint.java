//package com.wico.systemlinkweb.security.handle;
//
//import com.alibaba.fastjson.JSON;
//import com.wico.systemlinkweb.result.CodeMsg;
//import com.wico.systemlinkweb.result.Result;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author XuCheng
// * @Date 2020-7-1 15:37
// */
//@Component
//public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
////        JsonResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
//        Result result = Result.error(CodeMsg.USER_NOT_LOGIN);
//        httpServletResponse.setContentType("text/json;charset=utf-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
//    }
//}
