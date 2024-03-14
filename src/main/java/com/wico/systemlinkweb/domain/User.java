package com.wico.systemlinkweb.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author XuCheng
 * @Date 2020/4/26 15:32
 */
@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String username;
    private String password;
    private String manufacturerId;
    private Long bitmap;
    private Long updateTime;
}
