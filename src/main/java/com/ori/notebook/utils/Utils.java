package com.ori.notebook.utils;

import com.ori.notebook.model.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class Utils {
    public static String getCurUserId() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user.getId();
    }
}
