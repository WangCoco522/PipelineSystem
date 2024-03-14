package com.wico.systemlinkweb.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private int id;
    private String username;
    private String manufacturerName;
    private boolean isEnable;
    private boolean isAdmin;
    private String updateTime;

    public UserVO(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
