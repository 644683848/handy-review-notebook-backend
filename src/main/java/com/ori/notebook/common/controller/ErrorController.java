package com.ori.notebook.common.controller;

import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    //code是1 => token错误或过期, 否则表示未授权. 这是在shiro的过滤器工厂里配置的
    @RequestMapping(value="authError")
    public Result authError(int code) {
        return code ==1?new Result(ResultCode.UNAUTHENTICATED):new Result(ResultCode.UNAUTHORISE);
    }

}
