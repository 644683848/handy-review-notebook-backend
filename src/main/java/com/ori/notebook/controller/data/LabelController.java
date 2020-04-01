package com.ori.notebook.controller.data;

import com.ori.notebook.model.result.Result;
import com.ori.notebook.model.result.ResultCode;
import com.ori.notebook.service.data.LabelService;
import com.ori.notebook.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/data/label")
@Validated
public class LabelController {
    private LabelService labelService;
    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Result dropLabelById(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, labelService.dropLabel(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAllByUserId() {
        return new Result(ResultCode.SUCCESS, labelService.findAllByUserId(ShiroUtils.getCurUserId()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Map<String, String> map) {
        return new Result(ResultCode.SUCCESS, labelService.save(map, ShiroUtils.getCurUserId()));
    }
}
