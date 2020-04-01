package com.ori.notebook.service.data;

import com.ori.notebook.common.Exception.NoSuchIdException;
import com.ori.notebook.dao.data.LabelDao;
import com.ori.notebook.model.data.Label;
import com.ori.notebook.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LabelService {
    private LabelDao labelDao;
    private IdWorker idWorker;
    @Autowired
    public LabelService(LabelDao labelDao, IdWorker idWorker) {
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public Set<Label> findAllByUserId(String curUserId) {
        return labelDao.findAllByUserId(curUserId);
    }

    public Label dropLabel(String id) {
        if (labelDao.findById(id).isPresent()) {
            Label label = labelDao.findById(id).get();
            labelDao.deleteById(id);
            return label;
        }else {
            throw new NoSuchIdException("没有这个标签: " + id);
        }
    }

    public Label save(Map<String, String> map, String curUserId) {
        Label label = new Label();
        label.setLabelName(map.get("labelName"));
        label.setColor(map.get("color"));
        label.setId(idWorker.nextId() + "");
        label.setUserId(curUserId);
        labelDao.save(label);
        return label;
    }

    public List<Label> saveAll(List<Map<String, String>> maps, String curUserId) {
        List<Label> labels = new ArrayList<>();
        for (Map<String, String> map : maps) {
            Label label = new Label();
            label.setLabelName(map.get("labelName"));
            label.setColor(map.get("color"));
            label.setId(idWorker.nextId() + "");
            label.setUserId(curUserId);
            labels.add(label);
        }
        labelDao.saveAll(labels);
        return labels;
    }
}
