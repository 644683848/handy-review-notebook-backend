package com.ori.notebook.shiro;

import com.ori.notebook.utils.JwtUtils;
import com.ori.notebook.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class JWTFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 从头信息中获取jwt token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");
        if (token == null) {
            log.trace("token为空");
            return false;
        }
        Map<String, Object> principal;
        principal = JwtUtils.verifierToken(token);
        if (principal == null) {  // token验证失败, 在onAccessDenied中处理
            return false;
        } else {
            ShiroUtils.setPrincipal(principal);
            // 因为禁用了session, 所以只能自己手动在每次请求通过login方法初始化subject
            initSubject(new JWTUsernamePasswordToken(token));
            return true;
        }
    }

    // 借助login初始化subject
    private void initSubject(JWTUsernamePasswordToken token) {
        SecurityUtils.getSubject().login(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 因为全局@ExceptionHandler注解无法解析过滤器中的异常, 所以不能像下面这样在这里抛出			TokenEncodeException
        // throw new TokenEncodeException("token解析错误");
        // 而是通过重定向到自定义的url再处理错误
        redirectToLogin(request, response);
        return false;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.trace("清理了ThreadLocal");
        ShiroUtils.cleanUp();
    }
}