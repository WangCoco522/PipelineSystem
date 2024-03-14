package com.wico.systemlinkweb.result;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/26 15:54
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(){
    }

    public Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 成功得时候调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }


    /**
     * 失败得时候调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

}
