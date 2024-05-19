package com.mophn.controller;

import com.mophn.common.ResultVO;
import com.mophn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/getDataList")
    public ResultVO<List<Map<String, String>>> getDataList() {
        return new ResultVO<>(userService.getDataList());
    }
}
