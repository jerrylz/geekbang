package com.mophn.service;

import com.mophn.dao.UserDAO;
import com.mophn.entity.Address;
import com.mophn.entity.UserEntity;
import com.mophn.vo.UserVO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;


    public List<Map<String, String>> getDataList() {
        return userDAO.getDataList();
    }

//    @Transactional
    public int updateUser(String age, String prov) {
        Object o = AopContext.currentProxy();
        int i = userDAO.updateUser(age);
        updatePro(prov);
        return i;
    }

    @Transactional
    public int updatePro(String prov) {
        int a = 1/0;
        int i = userDAO.updatePro(prov);
        return i;
    }

    public Map<String, Object> getDataList2() {
        List<UserEntity> dataList2 = userDAO.getDataList2();
        HashSet<String> set = new HashSet<>();
        List<UserVO> collect = dataList2.stream().map(userEntity -> {
            UserVO userVO = new UserVO();
            HashMap<String, Object> map = new HashMap<>();
            for (Address address : userEntity.getAddress()) {
                map.put(address.getDat(), address.getAddr());
                set.add(address.getDat());
            }
            userVO.setName(userEntity.getName());
            userVO.setData(map);
            return userVO;
        }).collect(Collectors.toList());

        HashMap<String, Object> map = new HashMap<>();

        map.put("field", set.stream().sorted().collect(Collectors.toList()));
        map.put("data", collect);
        return map;
    }
}
