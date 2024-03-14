//package com.wico.systemlinkweb.security.handle;
//
//import com.wico.systemlinkweb.exception.GlobleException;
//import com.wico.systemlinkweb.mapper.ZwIpFilterMapper;
//import com.wico.systemlinkweb.result.CodeMsg;
//import com.wico.systemlinkweb.utils.IPUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Collection;
//import java.util.Iterator;
//
///**
// * @Author XuCheng
// * @Date 2020-7-2 10:25
// * 对于Security需重写AccessDeniedException、AccessDeniedException异常，暂未重写
// */
//@Component
//public class CustomizeAccessDecisionManager implements AccessDecisionManager{
//
//
//    @Autowired
//    HttpServletRequest request;
//
//    @Autowired
//    ZwIpFilterMapper zwIpFilterMapper;
//
//    @Override
//    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//        boolean hasAuthority = false;
//        Iterator<ConfigAttribute> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            ConfigAttribute ca = iterator.next();
//            //当前请求需要的权限
//            String needRole = ca.getAttribute();
//            //当前用户所具有的权限
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(needRole)) {
////                    return;
//                    hasAuthority = true;
//                    break;
//                }
//            }
//
//            //过滤ip,若用户在白名单内，则放行
//            String ipAddress= IPUtils.getRealIP(request);
//            if(!StringUtils.isNotBlank(ipAddress)){
//                throw new GlobleException(CodeMsg.SERVER_ERROR);
//            }
//            int mark = 1;
//            try {
//                mark = zwIpFilterMapper.select(ipAddress);
//            }catch (Exception e){
//                mark = zwIpFilterMapper.select("0.0.0.0");
//            }
//            if(mark == 0 && hasAuthority){
//                return;
//            }else {
//                throw new AccessDeniedException("ip已被拦截!");
//            }
//
//        }
//        throw new AccessDeniedException("权限不足!");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
