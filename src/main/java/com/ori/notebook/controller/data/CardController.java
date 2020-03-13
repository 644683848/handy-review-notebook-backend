package com.ori.notebook.controller.data;

import com.ori.notebook.model.data.Card;
import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import com.ori.notebook.service.data.CardService;
import com.ori.notebook.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/data/card")
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Result deleteCard(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, cardService.deleteCard(id));
    }

    @RequestMapping(path = "/review", method = RequestMethod.GET)
    public Result review() {
        return new Result(ResultCode.SUCCESS, cardService.review());
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public Result update(@RequestBody Map<String, String> map) {
        return new Result(ResultCode.SUCCESS, cardService.update(map));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{labelIds}/{startTime}/{endTime}")
    public Result findByLabelsAndTime(@PathVariable List<String> labelIds,
                                      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
                                      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime) {
        List<Card> byLabelAndTime = cardService.findByLabelAndTime(labelIds, startTime, endTime);
        System.out.println(byLabelAndTime.size());
        return new Result(ResultCode.SUCCESS, cardService.findByLabelAndTime(labelIds, startTime, endTime));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{startTime}/{endTime}")
    public Result findByTime( @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
                              @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime) {
        return new Result(ResultCode.SUCCESS, cardService.findByLabelAndTime(null, startTime, endTime));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{labelIds}")
    public Result findByLabels(@PathVariable List<String> labelIds) {
        return new Result(ResultCode.SUCCESS, cardService.findByLabelAndTime(labelIds, null, null));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addCard(@RequestBody Map<String, Object> map) {
        return new Result(ResultCode.SUCCESS, cardService.save(map, Utils.getCurUserId()));
    }

}
