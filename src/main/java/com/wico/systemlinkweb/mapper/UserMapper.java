package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.User;
import com.wico.systemlinkweb.vo.QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author XuCheng
 * @Date 2020/4/26 15:30
 */
@Mapper
public interface UserMapper {
    User findByUser(User gasUser);

    User selectByName(String userName);

    Integer insertOne(@Param("user") User user);

    int countAll();

    List<User> pagingAll(@Param("queryVo") QueryVo queryVo);

    User selectById(Long id);

    Integer updateBitmap(@Param("id") Long id, @Param("bitmap") Long bitmap, @Param("updateTime") Long updateTime);

    Integer updatePassword(@Param("id") Long id, @Param("password") String password, @Param("updateTime") Long updateTime);

    Integer deleteById(Long id);
}
