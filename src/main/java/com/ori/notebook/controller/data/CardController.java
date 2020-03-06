package com.ori.notebook.controller.data;

import com.ori.notebook.model.data.Card;
import com.ori.notebook.model.result.PageResult;
import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import com.ori.notebook.service.data.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/data/card")
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addCard(@RequestBody Map<String, Object> map) {
        return new Result(ResultCode.SUCCESS, cardService.save(map));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAllByUserId(int page, int size) {
        Page<Card> cardPage = cardService.findAllByUserId(page, size);
        return new Result(ResultCode.SUCCESS, new PageResult<>(cardPage.getTotalElements(), cardPage.getContent()));
    }
}
