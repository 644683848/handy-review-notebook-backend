package com.ori.notebook.utils.shiro.realm;

import com.ori.notebook.dao.system.UserDao;
import com.ori.notebook.model.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        if (token.getUsername() == null || token.getPassword() == null) {
            return null;
        }
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return new SimpleAuthenticationInfo(user, password, this.getName());
        }
        return null;
    }
}
