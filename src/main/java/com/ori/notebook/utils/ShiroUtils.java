package com.ori.notebook.utils;
import com.ori.notebook.model.system.User;

import java.util.HashMap;
import java.util.Map;

public class ShiroUtils {
    static private ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);
    public static User getCurUser() {
        return (User) threadLocal.get().get("curUser");
    }
    public static String getCurUserId() {
        return (String) threadLocal.get().get("userId");
    }
    public static String getCurUsername() {
        return (String) threadLocal.get().get("username");
    }
    public static Object getPrincipal() {
        return threadLocal.get();
    }
    public static void setCurUser(User curUser) {
        threadLocal.get().put("curUser", curUser);
    }
    public static void setPrincipal(Map<String, Object> principal) {
        threadLocal.get().put("principal", principal);
    }

    public static void cleanUp() {
        threadLocal.remove();
    }
}
