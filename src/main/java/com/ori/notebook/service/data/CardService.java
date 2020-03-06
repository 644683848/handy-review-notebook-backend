package com.ori.notebook.service.data;

import com.ori.notebook.common.Exception.NoSuchIdException;
import com.ori.notebook.dao.data.CardDao;
import com.ori.notebook.dao.data.LabelDao;
import com.ori.notebook.model.data.Card;
import com.ori.notebook.model.data.Label;
import com.ori.notebook.utils.IdWorker;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService {
    private CardDao cardDao;
    private LabelDao labelDao;
    private IdWorker idWorker;

    @Autowired
    public CardService(CardDao cardDao, LabelDao labelDao, IdWorker idWorker) {
        this.cardDao = cardDao;
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public Page<Card> findAllByUserId(int page, int size) {
        return cardDao.findAllByUserId(Utils.getCurUserId(), PageRequest.of(page - 1, size));
    }

    public Card save(Map<String, Object> map) {
        Card card = new Card();
        card.setId(idWorker.nextId() + "");
        card.setUserId(Utils.getCurUserId());
        card.setQuestion(map.get("question").toString());
        card.setAnswer(map.get("answer").toString());
        card.setCreateTime(new Date());
        card.setLabels(findLabelsById((List<String>) map.get("labels")));
        cardDao.save(card);
        return card;
    }

    private Set<Label> findLabelsById(List<String> ids) {
        if (ids == null) return null;
        Set<Label> set = new HashSet<>();
        for (String _id : ids) {
            Label label;
            if(labelDao.findById(_id).isPresent())
                label = labelDao.findById(_id).get();
            else
                throw new NoSuchIdException("没有这个标签: " + _id);
            set.add(label);
        }
        return set;
    }
}
