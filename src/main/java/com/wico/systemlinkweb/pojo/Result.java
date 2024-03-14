package com.wico.systemlinkweb.pojo;

import com.wico.systemlinkweb.types.ResultType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Neosgao
 * @date 16:23 2022/12/9
 **/
@Getter
public class Result implements Serializable {
    private final int code;
    private final String msg;
    private final Object data;

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(0, "", data);
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail(ResultType resultType) {
        return new Result(resultType.getCode(), resultType.getMsg(), null);
    }
}
