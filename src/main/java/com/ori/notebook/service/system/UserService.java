package com.ori.notebook.service.system;

import com.ori.notebook.dao.system.UserDao;
import com.ori.notebook.model.system.User;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public void changeNickname(String nickname) {
        User curUser = Utils.getCurUser();
        curUser.setNickname(nickname);
        userDao.save(curUser);
    }
}
