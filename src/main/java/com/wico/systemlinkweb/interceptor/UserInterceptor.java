package com.wico.systemlinkweb.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.constant.UserConst;
import com.wico.systemlinkweb.domain.User;
import com.wico.systemlinkweb.pojo.Result;
import com.wico.systemlinkweb.property.GasAdminProperties;
import com.wico.systemlinkweb.service.UserService;
import com.wico.systemlinkweb.types.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private GasAdminProperties properties;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String username;
//        if ("/user/login".equals(request.getServletPath())) {
//            username = request.getHeader(properties.getUsernameHeader());
//        } else {
//            username = (String) request.getAttribute(UserConst.USERNAME);
//        }
//        if (StringUtils.hasText(username)) {
//            User user = userService.getUserByUsername(username);
//            if (user == null) {
//                setResponse(response, ResultType.USER_NOT_EXISTED);
//                return false;
//            }
//            if (!userService.checkBitmap(user.getBitmap(), UserConst.BITMAP_TYPE_ENABLE)) {
//                setResponse(response, ResultType.USER_UNABLE);
//                return false;
//            }
//        } else {
//            setResponse(response, ResultType.USER_LOGIN_INFO_EMPTY);
//            return false;
//        }
//        return true;
//    }

    private void setResponse(HttpServletResponse response, ResultType resultType) throws IOException {
        Result result = Result.fail(resultType);
        response.setContentType("text/javascript;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
