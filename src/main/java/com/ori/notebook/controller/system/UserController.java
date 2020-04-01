package com.ori.notebook.controller.system;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import com.ori.notebook.service.system.UserService;
import com.ori.notebook.utils.JwtUtils;
import com.ori.notebook.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
@Slf4j
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/nickname" , method = RequestMethod.PATCH)
    public Result changeNickname (@RequestBody Map<String, String> map){
        userService.changeNickname(map.get("nickname"));
        return new Result(ResultCode.SUCCESS);
    }


    // TODO 参数验证
    @PostMapping(value = "/login")
    public Result login (@RequestBody Map<String, String> map) {
        //账号或密码错误在common中统一处理
        return new Result(ResultCode.SUCCESS, userService.login(map.get("username"), map.get("password")));
    }

    @PostMapping(value = "/register")
    public Result register(@RequestBody Map<String, String> map){
        return new Result(ResultCode.SUCCESS,
                userService.register(map.get("username"), map.get("nickname"), map.get("password") ));
    }
}
