package com.ori.notebook.service.data;

import com.ori.notebook.dao.data.LabelDao;
import com.ori.notebook.model.data.Label;
import com.ori.notebook.utils.IdWorker;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    private LabelDao labelDao;
    private IdWorker idWorker;
    @Autowired
    public LabelService(LabelDao labelDao, IdWorker idWorker) {
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public List<Label> findAllByUserId() {
        return labelDao.findAllByUserId(Utils.getCurUserId());
    }

    public Label save(Label label) {
        label.setId(idWorker.nextId() + "");
        label.setUserId(Utils.getCurUserId());
        labelDao.save(label);
        return label;
    }
}
