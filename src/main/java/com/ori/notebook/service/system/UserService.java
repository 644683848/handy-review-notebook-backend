package com.ori.notebook.service.system;

import com.ori.notebook.dao.system.UserDao;
import com.ori.notebook.model.data.Label;
import com.ori.notebook.model.system.User;
import com.ori.notebook.service.data.CardService;
import com.ori.notebook.service.data.LabelService;
import com.ori.notebook.utils.IdWorker;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private UserDao userDao;

    private IdWorker idWorker;
    private CardService cardService;
    private LabelService labelService;

    @Autowired
    public UserService(UserDao userDao, IdWorker idWorker, CardService cardService, LabelService labelService) {
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.cardService = cardService;
        this.labelService = labelService;
    }


    public void changeNickname(String nickname) {
        User curUser = Utils.getCurUser();
        curUser.setNickname(nickname);
        userDao.save(curUser);
    }

    public List<Map<String, Object>> register(String username, String nickname, String password) {
        String _id = idWorker.nextId() + "";
        User newUser = new User(_id, username, nickname, password);
        userDao.save(newUser);
        labelService.saveAll(getInitLabels(), _id);
        Set<Label> labels = labelService.findAllByUserId(_id);
        cardService.saveAll(getInitCards(labels), _id);
        return getInitCards(labels);
    }

    private List<Map<String, String>> getInitLabels() {
        List<Map<String, String>> res = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("labelName", "这是一个标签");
        map.put("color", "#FFFFFF");
        res.add(map);

        map = new HashMap<>();
        map.put("labelName", "蓝色的标签");
        map.put("color", "#6495ED");
        res.add(map);

        map = new HashMap<>();
        map.put("labelName", "新概念英语");
        map.put("color", "#FFB6C1");
        res.add(map);

        return res;
    }

    private List<Map<String, Object>> getInitCards(Set<Label> labels) {
        Iterator<Label> it = labels.iterator();
        Set<Label> tempSet = new HashSet<>();
        List<Map<String, Object>> res = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("question", "这是一个你的疑惑");
        map.put("answer", "这是你对于这个疑惑的思考");
        tempSet.add(it.next());
        map.put("labels", tempSet);
        res.add(map);
        tempSet = new HashSet<>();
        map = new HashMap<>();

        map.put("question", "点击右上角的解锁可以查看答案");
        map.put("answer", "这是一个答案");
        tempSet.add(it.next());
        tempSet.add(it.next());
        map.put("labels", tempSet);
        res.add(map);
        map = new HashMap<>();

        map.put("question", "这个列表里装了什么?");
        map.put("answer", "装着根据艾宾浩斯遗忘曲线来说, 你今天应该复习的所有卡片");
        map.put("labels", labels);
        res.add(map);
        return res;
    }
}
