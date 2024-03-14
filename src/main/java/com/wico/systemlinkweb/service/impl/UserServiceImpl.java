package com.wico.systemlinkweb.service.impl;

import com.wico.systemlinkweb.constant.UserConst;
import com.wico.systemlinkweb.domain.User;
import com.wico.systemlinkweb.mapper.UserMapper;
import com.wico.systemlinkweb.redis.RedisService;
import com.wico.systemlinkweb.service.UserService;
import com.wico.systemlinkweb.utils.MapBuilder;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author XuCheng
 * @Date 2020/4/26 15:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;

//    @Override
//    public String login(GasUser gasUser, HttpServletResponse response){
//        if (gasUser == null){
//            throw new GlobleException(CodeMsg.USER_ERROR);
//        }
//        String username = gasUser.getUsername();
//        String password = gasUser.getPassword();
//        if (username == null){
//            throw new GlobleException(CodeMsg.USER_EMPTY);
//        }
//        if (password == null){
//            throw new GlobleException(CodeMsg.PASSWORD_EMPTY);
//        }
//        GasUser dbGasUser = userMapper.findByUser(gasUser);
//        if (dbGasUser == null){
//            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
//        }
//        String token = UUIDUtils.getUUID();
//        addCookie(response, token, dbGasUser);
//        return token;
//    }


//    @Override
//    public GasUser getByToken(HttpServletResponse response, String token) {
//        if (StringUtils.isEmpty(token)){
//            return null;
//        }
//        GasUser gasUser =  redisService.get(UserKey.token,token, GasUser.class);
//        //延长有效期
//        if (gasUser != null){
//            addCookie(response,token, gasUser);
//        }
//        return gasUser;
//    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        Integer affectRowNum = userMapper.insertOne(user);
        return affectRowNum == 1;
    }

    @Override
    public PageBean<User> getUserPage(QueryVo queryVo) {
        User user = userMapper.selectByName(queryVo.getUsername());
        List<User> userList = Collections.singletonList(user);
        int total = 1;
        Map<String, Object> extraInfo = MapBuilder.<String, Object>builder().put(UserConst.IS_ADMIN, false).build();
        if (user != null && checkBitmap(user.getBitmap(), UserConst.BITMAP_TYPE_ADMIN)) {
            userList = userMapper.pagingAll(queryVo);
            total = userMapper.countAll();
            extraInfo.put(UserConst.IS_ADMIN, true);
        }
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setList(userList);
        pageBean.setTotalCount(total);
        pageBean.setExtraInfo(extraInfo);
        return pageBean;
    }

    @Override
    public boolean checkBitmap(Long bitmap, int bitmapType) {
        if (bitmap == null) {
            return false;
        }
        return (bitmap & (1L << bitmapType)) != 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean setBitmap(Long id, Long bitmap, int bitmapType) {
        if (bitmap == null) {
            return false;
        }
        bitmap |= (1L << bitmapType);
        return (1 == userMapper.updateBitmap(id, bitmap, System.currentTimeMillis()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean unsetBitmap(Long id, Long bitmap, int bitmapType) {
        if (bitmap == null) {
            return false;
        }
        bitmap &= ~(1L << bitmapType);
        return (1 == userMapper.updateBitmap(id, bitmap, System.currentTimeMillis()));
    }

    @Override
    public boolean enable(Long id) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }
        Long bitmap = Optional.ofNullable(user.getBitmap()).orElse(0L);
        return setBitmap(id, bitmap, UserConst.BITMAP_TYPE_ENABLE);
    }

    @Override
    public boolean unable(Long id) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }
        Long bitmap = Optional.ofNullable(user.getBitmap()).orElse(0L);
        return unsetBitmap(id, bitmap, UserConst.BITMAP_TYPE_ENABLE);
    }

    @Override
    public void modifyPassword(Long id, String encryptedPassword) {
        userMapper.updatePassword(id, encryptedPassword, System.currentTimeMillis());
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }


//    private void addCookie(HttpServletResponse response, String token, GasUser gasUser) {
//        redisService.set(UserKey.token,token, gasUser);
//        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
//        cookie.setMaxAge(UserKey.token.expireSeconds());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//    }
}
