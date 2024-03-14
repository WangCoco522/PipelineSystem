package com.wico.systemlinkweb.service;

import com.wico.systemlinkweb.domain.User;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.vo.QueryVo;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.vo.QueryVo;

public interface UserService {
    User getUserByUsername(String username);

    boolean addUser(User user);

    PageBean<User> getUserPage(QueryVo queryVo);

    boolean checkBitmap(Long bitmap, int bitmapType);

    boolean setBitmap(Long id, Long bitmap, int bitmapType);

    boolean unsetBitmap(Long id, Long bitmap, int bitmapType);

    boolean enable(Long id);

    boolean unable(Long id);

    void modifyPassword(Long id, String encryptedPassword);

    void delete(Long id);
}
