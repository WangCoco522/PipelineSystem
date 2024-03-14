package com.wico.systemlinkweb.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.component.JwtComponent;
import com.wico.systemlinkweb.constant.UserConst;
import com.wico.systemlinkweb.pojo.Result;
import com.wico.systemlinkweb.property.GasAdminProperties;
import com.wico.systemlinkweb.types.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Resource
    private GasAdminProperties properties;
    @Resource
    private JwtComponent jwtComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader(properties.getJwt().getHeader());
        if (!StringUtils.hasText(token)) {
            logger.debug("Token is empty");
            Result result = Result.fail(ResultType.USER_TOKEN_INVALIDED);
            response.setContentType("text/javascript;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }
        JwtComponent.JwtHelper helper = jwtComponent.helper(token);
        if (helper.isExpired()) {
            logger.debug("Token has expired, token={}", token);
            Result result = Result.fail(ResultType.USER_TOKEN_EXPIRED);
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }
        request.setAttribute(UserConst.USERNAME, helper.getValue(UserConst.USERNAME));
        request.setAttribute(UserConst.MANUFACTURER_ID, helper.getValue(UserConst.MANUFACTURER_ID));
        return true;
    }
}
