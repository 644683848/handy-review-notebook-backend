package com.ori.notebook.service.data;

import com.ori.notebook.common.Exception.NoSuchIdException;
import com.ori.notebook.dao.data.CardDao;
import com.ori.notebook.dao.data.LabelDao;
import com.ori.notebook.model.data.Card;
import com.ori.notebook.model.data.Label;
import com.ori.notebook.utils.IdWorker;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDate;
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

    public Card deleteCard(String id) {
        if (cardDao.findById(id).isPresent()) {
            Card card = cardDao.findById(id).get();
            cardDao.deleteById(id);
            return card;
        } else {
            throw new NoSuchIdException("没有这个id:" + id);
        }
    }

    // 根据记忆曲线查找需要复习的所有卡片
    public List<Card> review() {
        List<LocalDate> allNeedReviewDate = getAllNeedReviewDate();
        return cardDao.findAllByUserIdAndCreateTimeIn(Utils.getCurUserId(), allNeedReviewDate);
    }

    public Card update(Map<String, String> map) {
        String curId = map.get("id");
        String newQuestion = map.get("newQuestion");
        String newAnswer = map.get("newAnswer");
        if (cardDao.findById(curId).isPresent()) {
            Card oldCard = cardDao.findById(curId).get();
            oldCard.setQuestion(newQuestion);
            oldCard.setAnswer(newAnswer);
            cardDao.save(oldCard);
            return oldCard;
        } else {
            throw new NoSuchIdException("没有这个id: "+ curId);
        }
    }

    public List<Card> findByLabelAndTime(List<String> labelIds, LocalDate startTime, LocalDate endTime) {
        return cardDao.findAll((Specification<Card>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            criteriaQuery.distinct(true);
            predicates.add(criteriaBuilder.equal(root.get("userId"), Utils.getCurUserId()));
            if (labelIds != null && !labelIds.isEmpty()) {
                Join<Card, Label> join = root.join("labels", JoinType.INNER);
                predicates.add(join.get("id").in(labelIds));
            }
            if (startTime != null && endTime != null) {
                predicates.add(criteriaBuilder.between(root.get("createTime"), startTime, endTime));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));});
    }

    public Card save(Map<String, Object> map, String curUserId) {
        Card card = initCard(curUserId, map.get("question"), map.get("answer"), findLabelsById((List<String>) map.get("labels")));
        cardDao.save(card);
        return card;
    }

    private List<LocalDate> getAllNeedReviewDate() {
        List<LocalDate> res = new ArrayList<>();
        List<Integer> reviewCycle = Arrays.asList(0, 1, 2, 4, 7, 15, 30);
        LocalDate now = LocalDate.now();
        for (Integer integer : reviewCycle) {
            res.add(now.minusDays(integer));
        }
        return res;
    }


    public List<Card> saveAll(List<Map<String, Object>> maps, String curUserId) {
        List<Card> cards = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Card card = initCard(curUserId, map.get("question").toString(),
                    map.get("answer").toString(), (Set<Label>) map.get("labels"));
            cards.add(card);
        }
        cardDao.saveAll(cards);
        return cards;
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

    private Card initCard(String curUserId, Object question, Object answer, Set<Label> labels) {
        Card card = new Card();
        card.setId(idWorker.nextId() + "");
        card.setUserId(curUserId);
        card.setQuestion(question.toString());
        card.setAnswer(answer.toString());
        card.setCreateTime(LocalDate.now());
        card.setLabels(labels);
        return card;
    }
}
