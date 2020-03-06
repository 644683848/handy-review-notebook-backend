package com.ori.notebook.common;

import com.ori.notebook.common.Exception.NoSuchIdException;
import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class CommonExceptionHandler {
    //处理未找到Id异常
    @ExceptionHandler(value = NoSuchIdException.class)
    public Result noSuchIdException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        System.out.println("noSuchIdException: 没有找到这个标签");
        return new Result(ResultCode.FAIL, e.getMessage());
    }

    //处理账号或密码错误
    @ExceptionHandler(value = UnknownAccountException.class)
    public Result unknownAccountException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return new Result(ResultCode.LOGINERR);
    }
}
