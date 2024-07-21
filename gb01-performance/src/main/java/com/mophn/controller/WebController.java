package com.mophn.controller;

import com.mophn.common.ResultVO;
import com.mophn.entity.UserEntity;
import com.mophn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/getDataList2")
    public ResultVO<Map<String, Object>> getDataList2() {
        return new ResultVO<>(userService.getDataList2());
    }

    @GetMapping("/updateUser/{age}/{prov}")
    public int updateUser(@PathVariable("age") String age, @PathVariable("prov") String prov) {
        return userService.updateUser(age, prov);
    }
}
