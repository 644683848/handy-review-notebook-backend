package com.ori.notebook.service.data;

import com.ori.notebook.dao.data.CardDao;
import com.ori.notebook.model.data.Card;
import com.ori.notebook.utils.IdWorker;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardService {
    private CardDao cardDao;
    private IdWorker idWorker;

    @Autowired
    public CardService(CardDao cardDao, IdWorker idWorker) {
        this.cardDao = cardDao;
        this.idWorker = idWorker;
    }

    public Card save(Card card) {
        card.setId(idWorker.nextId() + "");
        card.setUserId(Utils.getCurUsername());
        card.setCreateTime(new Date());
        System.out.println(new Date());
        cardDao.save(card);
        return card;
    }
}
