package com.wico.systemlinkweb.controller;

import com.wico.systemlinkweb.component.JwtComponent;
import com.wico.systemlinkweb.constant.UserConst;
import com.wico.systemlinkweb.domain.User;
import com.wico.systemlinkweb.pojo.Result;
import com.wico.systemlinkweb.property.GasAdminProperties;
import com.wico.systemlinkweb.service.UserService;
import com.wico.systemlinkweb.types.ResultType;
import com.wico.systemlinkweb.utils.MapBuilder;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.utils.SecureUtil;
import com.wico.systemlinkweb.utils.TimeCovert;
import com.wico.systemlinkweb.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api("用户数据接口")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private JwtComponent jwtComponent;
    /*@Resource
    private ManufacturerService manufacturerService;*/
    @Resource
    private GasAdminProperties properties;

    @CrossOrigin
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@Valid LoginVO loginVO, HttpServletResponse response, BindingResult bindingResult,HttpServletRequest request) {


        if (bindingResult.hasErrors()) {
            return Result.fail(ResultType.USER_LOGIN_INFO_EMPTY);
        }

        // 从会话中获取之前存储的验证码
        String sessionCaptcha = (String) request.getSession().getAttribute("verifyCode");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(loginVO.getCaptcha())) {
            return Result.fail(ResultType.USER_CAPTCHA_ERROR);
        }

        // 校验密码长度和组成要求
        String password = loginVO.getPassword();
        if (password.length() < 8 || password.length() > 30 || !isPasswordValid(password)) {
            return Result.fail(ResultType.USER_PASSWORD_ERROR);
        }
        User user = userService.getUserByUsername(loginVO.getUsername());
        // UserInterceptor已判定user不为null
        //String password = loginVO.getPassword();
        String encryptedPassword = SecureUtil.md5(password);
        System.out.println("-----" +encryptedPassword);
        System.out.println("-----" +user.toString());

        //boolean isAdmin = userService.checkBitmap(user.getBitmap(), UserConst.BITMAP_TYPE_ADMIN);
        Assert.notNull(encryptedPassword, "Password is null, origin={}" + password);
        if (!encryptedPassword.equals(user.getPassword())){
            return Result.fail(ResultType.USER_PASSWORD_ERROR);
        }
        Map<String, Object> userInfo = MapBuilder.<String, Object>builder()
                .put(UserConst.USERNAME, user.getUsername())
                .put(UserConst.MANUFACTURER_ID, user.getManufacturerId())
                .build();
        String token = jwtComponent.createToken(userInfo);
        response.setHeader(jwtComponent.getHeader(), token);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, jwtComponent.getHeader());
        logger.info("User logins, username={}", user.getUsername());
        return Result.success();
    }
    private boolean isPasswordValid(String password) {
        int charTypesCount = 0;
        if (password.matches(".*\\d.*")) { // 包含数字
            charTypesCount++;
        }
        if (password.matches(".*[a-z].*")) { // 包含小写字母
            charTypesCount++;
        }
        if (password.matches(".*[A-Z].*")) { // 包含大写字母
            charTypesCount++;
        }
        if (password.matches(".*[^a-zA-Z0-9].*")) { // 包含特殊字符
            charTypesCount++;
        }
        return charTypesCount >= 3;
    }


    @CrossOrigin
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@Valid RegisterVO registerVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(ResultType.USER_REGISTER_INFO_EMPTY);
        }
        User user = userService.getUserByUsername(registerVO.getUsername());
        if (user != null) {
            return Result.fail(ResultType.USER_EXISTED);
        }
        // 密码校验
        if (!registerVO.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
            return Result.fail(ResultType.USER_PASSWORD_ERROR);
        }

        user = new User().setUsername(registerVO.getUsername())
                .setPassword(SecureUtil.md5(registerVO.getPassword()))
                .setBitmap(0L)
                .setUpdateTime(System.currentTimeMillis());
        boolean isRegistered = userService.addUser(user);
        if (!isRegistered) {
            return Result.fail(ResultType.USER_REGISTER_FAILED);
        }
        logger.info("New user has registered, user={}", user);
        return Result.success();
    }

    @CrossOrigin
    @GetMapping("/list")
    @ApiOperation("拉取用户列表")
    public Result list(QueryVo queryVo) {
        PageBean<UserVO> userVOPageBean = new PageBean<>();
        userVOPageBean.setPageSize(queryVo.getPageSize());
        userVOPageBean.setCurrentPage(queryVo.getCurrentPage());
        PageBean<User> userPageBean = userService.getUserPage(queryVo);
        if (userPageBean == null) {
            return Result.success(userVOPageBean);
        }
        userVOPageBean.setTotalCount(userPageBean.getTotalCount());
        userVOPageBean.setExtraInfo(userPageBean.getExtraInfo());
        if (CollectionUtils.isEmpty(userPageBean.getList())) {
            return Result.success(userVOPageBean);
        }
   //     Map<String, Manufacturer> manufacturerMap = manufacturerService.getManufacturerMap();
        List<UserVO> userVOList = new ArrayList<>(userPageBean.getList().size());
        for (User user : userPageBean.getList()) {
            if (user == null) {
                continue;
            }
            UserVO userVO = new UserVO(user.getId(), user.getUsername());
       //     Optional.ofNullable(manufacturerMap.get(user.getManufacturerId())).ifPresent(m -> userVO.setManufacturerName(m.getName()));
            userVO.setEnable(userService.checkBitmap(user.getBitmap(), UserConst.BITMAP_TYPE_ENABLE));
            userVO.setAdmin(userService.checkBitmap(user.getBitmap(), UserConst.BITMAP_TYPE_ADMIN));
            Optional.ofNullable(user.getUpdateTime()).ifPresent(u -> userVO.setUpdateTime(TimeCovert.stampToDate(u.toString())));
            userVOList.add(userVO);
        }
        userVOPageBean.setList(userVOList);
        return Result.success(userVOPageBean);
    }

    @CrossOrigin
    @PutMapping("/enable/{id}")
    @ApiOperation("激活用户账号")
    public Result enable(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.success();
        }
        if (userService.enable(id)) {
            return Result.success();
        }
        return Result.fail(ResultType.USER_ENABLE_ERROR);
    }

    @CrossOrigin
    @PutMapping("/unable/{id}")
    @ApiOperation("禁用用户账号")
    public Result unable(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.success();
        }
        if (userService.unable(id)) {
            return Result.success();
        }
        return Result.fail(ResultType.USER_UNABLE_ERROR);
    }

    @CrossOrigin
    @PutMapping("/password")
    @ApiOperation("修改密码")
    public Result modifyPassword(ModifyPasswordVO passwordVO) {
        if (!StringUtils.hasText(passwordVO.getNewPassword())) {
            return Result.fail(ResultType.USER_NEW_PASSWORD_EMPTY);
        }
        String encryptedPassword = SecureUtil.md5(passwordVO.getNewPassword());
        userService.modifyPassword(passwordVO.getId(), encryptedPassword);
        return Result.success();
    }

    @CrossOrigin
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除账号")
    public Result remove(@PathVariable("id") Long id) {
        userService.delete(id);
        return Result.success();
    }
}
