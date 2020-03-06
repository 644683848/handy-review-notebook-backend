package com.ori.notebook.controller.system;

import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys")
public class UserController {
    @Value("${user.timeout}")
    private long timeout;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(map.get("username"), map.get("password"));
        //账号或密码错误在common中统一处理
        subject.login(usernamePasswordToken);
        //设置超时时间
        subject.getSession().setTimeout(timeout);
        return new Result(ResultCode.SUCCESS, subject.getSession().getId());
    }
}
