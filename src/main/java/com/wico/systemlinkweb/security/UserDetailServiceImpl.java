//package com.wico.systemlinkweb.security;
//
//import com.wico.systemlinkweb.domain.GasPermission;
//import com.wico.systemlinkweb.domain.GasUser;
//import com.wico.systemlinkweb.exception.GlobleException;
//import com.wico.systemlinkweb.redis.RedisService;
//import com.wico.systemlinkweb.redis.UserKey;
//import com.wico.systemlinkweb.result.CodeMsg;
//import com.wico.systemlinkweb.service.IPermissionService;
//import com.wico.systemlinkweb.service.IUserService;
//import com.wico.systemlinkweb.utils.UUIDUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author XuCheng
// * @Date 2020-7-2 11:09
// */
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    IUserService userService;
//
//    @Autowired
//    IPermissionService permissionService;
//
//    @Autowired
//    RedisService redisService;
//
//    public static final String COOKIE_NAME_TOKEN = "token";
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username == null || "".equals(username)){
//            throw new GlobleException(CodeMsg.USER_EMPTY);
//        }
//        GasUser gasUser = userService.selectByName(username);
//        if (gasUser == null){
//            throw new GlobleException(CodeMsg.USER_ERROR);
//        }
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        if (gasUser != null){
//            //获取该用户所拥有的权限
//            List<GasPermission> sysPermissions = permissionService.selectListByUser(gasUser.getId());
//            // 声明用户授权
//            sysPermissions.forEach(sysPermission -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
//                grantedAuthorities.add(grantedAuthority);
//            });
//        }
//        return new User(gasUser.getUsername(), gasUser.getPassword(),grantedAuthorities);
//    }
//}
