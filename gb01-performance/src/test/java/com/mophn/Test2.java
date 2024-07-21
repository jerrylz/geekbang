package com.mophn;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test2 {
    public static void main(String[] args) throws JsonProcessingException {
        // 示例JSON数据
        String json = "{\"username\":\"JohnDoe\", \"user_name\":\"JaneDoe\"}";
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        System.out.println(user.getName());


    }
}
