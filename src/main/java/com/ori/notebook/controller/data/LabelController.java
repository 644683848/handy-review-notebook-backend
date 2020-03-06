package com.ori.notebook.controller.data;

import com.ori.notebook.model.data.Label;
import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import com.ori.notebook.service.data.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/label")
@Validated
public class LabelController {
    private LabelService labelService;
    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAllByUserId() {
        return new Result(ResultCode.SUCCESS, labelService.findAllByUserId());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Label label) {
        return new Result(ResultCode.SUCCESS, labelService.save(label));
    }
}
