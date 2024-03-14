package com.wico.systemlinkweb.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Neosgao
 * @date 16:00 2022/12/9
 **/
@Data
public class RegisterVO {
    @NotBlank
    private String username;
//    @NotBlank
    private String password;
   /*
    @NotBlank
    private String manufacturerId;
    */
}
