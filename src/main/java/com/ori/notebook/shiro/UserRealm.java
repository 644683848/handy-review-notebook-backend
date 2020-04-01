package com.ori.notebook.shiro;

import com.auth0.jwt.JWT;
import com.ori.notebook.dao.system.UserDao;
import com.ori.notebook.model.system.User;
import com.ori.notebook.utils.JwtUtils;
import com.ori.notebook.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    public void setName(String name) {
        super.setName("NotebookRealm");
    }
    //权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof JWTUsernamePasswordToken) {  // 仅用于给非登录请求初始化subject
            JWTUsernamePasswordToken token = (JWTUsernamePasswordToken)authenticationToken;
            return new SimpleAuthenticationInfo(ShiroUtils.getPrincipal(), token.getCredentials(), this.getName());
        } else {  // 登录请求走这里
            return new SimpleAuthenticationInfo(ShiroUtils.getPrincipal(), ShiroUtils.getCurUser().getPassword(), this.getName());
        }
    }
}
