//package com.wico.systemlinkweb.exception;
//
//import com.wico.systemlinkweb.result.CodeMsg;
//import com.wico.systemlinkweb.result.Result;
//import org.springframework.validation.BindException;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.naming.AuthenticationException;
//import javax.servlet.http.HttpServletRequest;
//import java.nio.file.AccessDeniedException;
//import java.util.List;
//
///**
// * @Author XuCheng
// * @Date 2020-6-9 12:46
// * 捕获全局异常
// */
//@ControllerAdvice
//@ResponseBody
//public class GlobleExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
//        if (e instanceof GlobleException){
//            GlobleException ex = (GlobleException)e;
//            return Result.error(ex.getCodeMsg());
//        } else if (e instanceof BindException){
//            BindException ex = (BindException)e;
//            List<ObjectError> errors = ex.getAllErrors();
//            ObjectError error = errors.get(0);
//            String msg = error.getDefaultMessage();
//            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
//          }
////        else if (e instanceof AuthenticationException){
////            AuthenticationException ex = (AuthenticationException)e;
////            String msg = ex.getMessage();
////            return Result.error(CodeMsg.NO_AUTHENTICATION.fillArgs(msg));
////        }else if (e instanceof AccessDeniedException){
////            AccessDeniedException ex = (AccessDeniedException)e;
////            String msg = ex.getMessage();
////            return Result.error(CodeMsg.NO_AUTHENTICATION.fillArgs(msg));
////        }
//        else {
//            return Result.error(CodeMsg.SERVER_ERROR);
//        }
//    }
//}
