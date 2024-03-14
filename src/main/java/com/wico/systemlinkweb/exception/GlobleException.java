package com.wico.systemlinkweb.exception;

import com.wico.systemlinkweb.result.CodeMsg;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020-6-9 13:03
 */
@Data
public class GlobleException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobleException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
