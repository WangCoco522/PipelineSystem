//package com.wico.systemlinkweb.security.handle;
//
//import com.alibaba.fastjson.JSON;
//import com.wico.systemlinkweb.domain.GasUser;
//import com.wico.systemlinkweb.result.Result;
//import com.wico.systemlinkweb.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//
///**
// * @Author XuCheng
// * @Date 2020-7-1 15:55
// */
//@Component
//public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    @Autowired
//    IUserService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        //更新用户表上次登录时间、更新人、更新时间等字段
//        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        GasUser gassUser = userService.selectByName(userDetails.getUsername());
//
//
//
//        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
//        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
//        //返回json数据
//        httpServletResponse.setContentType("text/json;charset=utf-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(true)));
//    }
//}
